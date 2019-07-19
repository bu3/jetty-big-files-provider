https://app.codeship.com/projects/908ff6d0-8c50-0137-c653-5ef8f12921fc/status?branch=master

This is a project I made to provide an example of the issue I'm facing with Jetty 9.4.x to serve large files

#Scenario 

1. Run the script `create-bigfiles.sh` to add large files in the shared directory.
2. Start the application running com.acme.Main
3. Open http://localhost:8080
4. Try to download file10G.txt. It gets truncated to 1.8G
