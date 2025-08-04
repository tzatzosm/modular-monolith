#!/usr/bin/env python3

import os


def add_include_to_settings(settings_file, feature_name):
    if feature_name and os.path.isfile(settings_file):
        with open(settings_file, "r") as f:
            lines = f.readlines()
        # Find the last include statement
        last_include_idx = None
        for idx, line in enumerate(lines):
            if line.strip().startswith("include "):
                last_include_idx = idx
        new_line = f"include 'features:{feature_name}'\n"
        if last_include_idx is not None:
            # Ensure a newline after the last include if not present
            if not lines[last_include_idx].endswith("\n"):
                lines[last_include_idx] = lines[last_include_idx] + "\n"
            lines.insert(last_include_idx + 1, new_line)
        else:
            # Ensure file ends with a newline before appending
            if lines and not lines[-1].endswith("\n"):
                lines[-1] = lines[-1] + "\n"
            lines.append(new_line)
        with open(settings_file, "w") as f:
            f.writelines(lines)
        print(f"[INFO] Added include 'features:{feature_name}' to {settings_file} after last include statement.")
    else:
        print("[WARN] Feature name or settings.gradle not found. Skipping include.")

def add_dependency_to_bootstrap(bootstrap_build_file, feature_name):
    if feature_name and os.path.isfile(bootstrap_build_file):
        with open(bootstrap_build_file, "r") as f:
            lines = f.readlines()
        # Find the last feature dependency after /* Features */
        in_features = False
        last_feature_idx = None
        features_comment_idx = None
        for idx, line in enumerate(lines):
            if '/* Features */' in line:
                in_features = True
                features_comment_idx = idx
            if in_features and "implementation project(':features:" in line:
                last_feature_idx = idx
        new_dep = f"    implementation project(':features:{feature_name}')\n"
        inserted = False
        if last_feature_idx is not None:
            lines.insert(last_feature_idx + 1, new_dep)
            inserted = True
        elif features_comment_idx is not None:
            lines.insert(features_comment_idx + 1, new_dep)
            inserted = True
        if inserted:
            with open(bootstrap_build_file, "w") as f:
                f.writelines(lines)
            print(f"[INFO] Added implementation project(':features:{feature_name}') to {bootstrap_build_file} after last feature dependency.")
        else:
            print(f"[WARN] Could not find /* Features */ comment in {bootstrap_build_file}. Skipping dependency add.")
    else:
        print("[WARN] Feature name or bootstrap/app/build.gradle not found. Skipping dependency add.")

def main():
    feature_name = "{{cookiecutter.feature_name}}"
    print(f"Adding feature '{feature_name}' to parent settings.gradle")
    parent_dir = os.path.abspath(os.path.join(os.getcwd(), os.pardir, os.pardir))
    settings_file = os.path.join(parent_dir, "settings.gradle")
    bootstrap_build_file = os.path.join(parent_dir, "bootstrap", "app", "build.gradle")
    add_include_to_settings(settings_file, feature_name)
    add_dependency_to_bootstrap(bootstrap_build_file, feature_name)

if __name__ == "__main__":
    main()
