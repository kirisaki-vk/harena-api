package com.harena.api.service;

import com.harena.api.file.BucketComponent;
import com.harena.api.utils.StringNormalizer;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.serialisation.Serialiseur;

@Log
@Service
@RequiredArgsConstructor
public class PatrimoineService {
  private final String PATRIMOINE_KEY = "patrimoines.db";
  private final BucketComponent bucketComponent;
  private final Serialiseur<HashMap<String, Patrimoine>> serializer = new Serialiseur<>();

  @SneakyThrows
  public Optional<Patrimoine> getPatrimone(String patrimoineName) {
    String id = StringNormalizer.apply(patrimoineName);
    File patrimoineFile = bucketComponent.download(PATRIMOINE_KEY);
    if (!patrimoineFile.exists()) {
      return Optional.empty();
    }

    HashMap<String, Patrimoine> patrimoine =
        serializer.deserialise(Files.readString(patrimoineFile.toPath()));
    return Optional.ofNullable(patrimoine.get(id));
  }

  @SneakyThrows
  public List<Patrimoine> savePatrimoines(List<Patrimoine> toSavePatrimoines) {
    File oldPatrimoinesFile = bucketComponent.download(PATRIMOINE_KEY);
    if (!oldPatrimoinesFile.exists()) {
      oldPatrimoinesFile.createNewFile();
      HashMap<String, Patrimoine> emptyPatrimoine = new HashMap<>();
      Files.writeString(oldPatrimoinesFile.toPath(), serializer.serialise(emptyPatrimoine));
    }

    String oldPatrimoineString = Files.readString(oldPatrimoinesFile.toPath());
    HashMap<String, Patrimoine> patrimoines = serializer.deserialise(oldPatrimoineString);
    toSavePatrimoines.forEach(
        patrimoine -> {
          patrimoines.put(StringNormalizer.apply(patrimoine.nom().toLowerCase()), patrimoine);
        });

    String newPatrimoineString = serializer.serialise(patrimoines);
    Path tmpFile = Files.createTempFile(PATRIMOINE_KEY, null);
    File serializedPatrimoine = Files.writeString(tmpFile, newPatrimoineString).toFile();
    bucketComponent.upload(serializedPatrimoine, PATRIMOINE_KEY);
    return toSavePatrimoines;
  }

  @SneakyThrows
  public List<Patrimoine> getAllPatrimoine() {
    File patrimoinesFile = bucketComponent.download(PATRIMOINE_KEY);
    if (!patrimoinesFile.exists()) {
      return List.of();
    }
    HashMap<String, Patrimoine> patrimoines =
        serializer.deserialise(Files.readString(patrimoinesFile.toPath()));
    return patrimoines.values().stream().toList();
  }
}
