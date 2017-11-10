#!/usr/bin/env bash

mvn clean appengine:deploy -DcloudSdkPath="$(gcloud --format='value(installation.sdk_root)' info)"
