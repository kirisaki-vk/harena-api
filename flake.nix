{
  description = "Flake devShell for poja developement";

  inputs = { utils.url = "github:numtide/flake-utils"; };

  outputs = { self, nixpkgs, utils, ... }:
    utils.lib.eachDefaultSystem (system:
      let
        pkgs = import nixpkgs { inherit system; };
        pythonPackages = pkgs.python3Packages;
      in {
        devShells.default = pkgs.mkShell {
          name = "poja";
          venvDir = "./.venv";
          buildInputs = with pkgs; [
            awscli2
            pythonPackages.python
            pythonPackages.venvShellHook
            corretto21
            maven
          ];

          GIT_SSL_NO_VERIFY = 1;

          # Run this command, only after creating the virtual environment
          postVenvCreation = ''
            unset SOURCE_DATE_EPOCH
          '';
          # Now we can execute any commands within the virtual environment.
          # This is optional and can be left out to run pip manually.
          postShellHook = ''
            # allow pip to install wheels
            unset SOURCE_DATE_EPOCH
          '';
        };
      });
}
