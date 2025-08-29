#!/usr/bin/env python3

import os
import subprocess
import sys

def rename_github_directory():
    """Rename github directory to .github if it exists."""
    if os.path.exists("github"):
        try:
            os.rename("github", ".github")
            print("Renamed 'github' directory to '.github'")
        except OSError as e:
            print(f"[ERROR] Failed to rename github directory: {e}")
            sys.exit(1)

def run_smoke_tests():
    """Run gradle clean build as smoke test."""
    print("Running smoke test")
    try:
        subprocess.check_call(["./gradlew", "clean", "build"])
    except subprocess.CalledProcessError as e:
        print(f"[ERROR] Gradle build failed: {e}")
        sys.exit(1)

def initialize_git_repo():
    """Initialize git repository if version control is set to git."""
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

def main():
    print("Making sure smoke test passes and we can build a distribution tarball ...")
    rename_github_directory()
    run_smoke_tests()
    initialize_git_repo()

if __name__ == "__main__":
    main()