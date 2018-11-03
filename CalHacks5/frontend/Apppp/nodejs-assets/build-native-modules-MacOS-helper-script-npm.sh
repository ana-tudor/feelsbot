#!/bin/bash
      # Helper script for Gradle to call npm on macOS in case it is not found
      export PATH=$PATH:/Users/hoaiandang/.nvm/versions/node/v10.9.0/lib/node_modules/npm/node_modules/npm-lifecycle/node-gyp-bin:/Users/hoaiandang/Documents/Github/feelsbot/CalHacks5/frontend/Apppp/node_modules/nodejs-mobile-react-native/node_modules/.bin:/Users/hoaiandang/Documents/Github/feelsbot/CalHacks5/frontend/Apppp/node_modules/.bin:/Users/hoaiandang/.nvm/versions/node/v10.9.0/bin:/Users/hoaiandang/anaconda/bin:/Library/Frameworks/Python.framework/Versions/3.6/bin:/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:/usr/local/share/dotnet:~/.dotnet/tools:/Library/Frameworks/Mono.framework/Versions/Current/Commands
      npm $@
    