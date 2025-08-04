#!/usr/bin/env python3

import os
import subprocess
import sys

def main():
    print("Making sure smoke test passes and we can build a distribution tarball ...")

    print("Running smoke test")
    try:
        subprocess.check_call(["./gradlew", "clean", "build"])
    except subprocess.CalledProcessError as e:
        print(f"[ERROR] Gradle build failed: {e}")
        sys.exit(1)

    print("Adding all files to git")
    version_control = "{{cookiecutter.version_control}}"
    if version_control == "git":
        print("Initializing git repo")
        git = os.environ.get("GIT") or subprocess.getoutput("which git")
        if git and os.path.isfile(git) and os.access(git, os.X_OK):
            try:
                subprocess.check_call([git, "init"])
                subprocess.check_call([git, "add", "."])
                subprocess.check_call([git, "commit", "-a", "-m", "Initial commit"])
            except subprocess.CalledProcessError as e:
                print(f"[ERROR] Git command failed: {e}")
        else:
            print("WARNING: git requested, but git binary not found", file=sys.stderr)

if __name__ == "__main__":
    main()