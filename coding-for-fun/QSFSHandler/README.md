### install

follow these steps to install:

1- make sure that JAVA JRE and scala SDK are installed and added to the classpath. if they are installed and are not
   in the classpath modify the paths in the scripts found in bin/ directory

2- give the execution right to scripts in bin:
   chmod +x bin/*

3- build using bin/package script (you can also use the build tool ant with the conf file build.xml but the final jar
is not working well):
   bin/package.sh

### launch

1- execute the server, then the client in different terminals to test a server/client communication and that all is okey
   bin/server.sh
   bin/server.sh

2- make sure that telnet or other server-client communication tool is installed. if not install telnet
   sudo port install telnet
   or
   brew install telnet (if you have brew)

3- start the server if not started
   bin/server.sh

4- start telnet on localhost and port 8030
   telnet 127.0.0.1 8030

5- manage the fileSystem using these commands (see QuickServerFSServer to modify or add functions)
   help
   set rootdir <path_to_root_dir>
   get rootdir
   mkdir <dir>: not implemented, because not required
   ls <dir> | <file>
   cat <path_to_file>

NB: to be able to access a remote machine, change localhost to ip adress of the machine to communicate with