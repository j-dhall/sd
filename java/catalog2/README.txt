Progress and Observations:

1. Generated build.gradle from Spring Initializr.
2. Added flyway to build.gradle.
3. Added persistence properties to application.properties. Created application-test.properties.
   Added a test case file CreateEntityIntegrationTest.java to test schema creation/validation/drop without actually creating an entity class.
4. Ran the bootRun task. No exceptions (like Unix or tomcat dll exceptions in the catalog project). 
   *Eclipse is stuck at bootRun (56%) even though the server ping at localhost:8080 behaves as expected without endpoints (HTTP 404 - not found).
   Running 'gradle bootRun' from command-line works fine. (no endless loop of logs like in the catalog project).
5. Created a controller (CategoryController) to demo logging. 
   Used lombak's @Slf4j that defaults to using Logback implementation (other implementation is Log4J using @CommonsLog).
   The log messages were printed on the console when the end point was accessed.
   Added server logging properties. The access logs appear under ./tomcat/logs in the project directory. The server logs appear on the console.
6. The following properties cause exceptions (the ones observed in the catalog project)
   ***logging.level.org.apache.tomcat=DEBUG***
   ***logging.level.org.apache.catalina=DEBUG***
   
   Exceptions:
   org.apache.tomcat.jni.LibraryNotFoundError: Can't load library: C:\mydrive\git\sd\java\catalog2\bin\**tcnative-1.dll**, Can't load library: C:\mydrive\git\sd\java\catalog2\bin\libtcnative-1.dll, no tcnative-1 in java.library.path: C:\Users\jeete\Downloads\eclipse-jee-2021-03-R-win32-x86_64\eclipse\plugins\org.eclipse.justj.openjdk.hotspot.jre.full.win32.x86_64_15.0.2.v20210201-0955\jre\bin;C:\Windows\Sun\Java\bin;C:\Windows\system32;C:\Windows;C:/Users/jeete/Downloads/eclipse-jee-2021-03-R-win32-x86_64/eclipse//plugins/org.eclipse.justj.openjdk.hotspot.jre.full.win32.x86_64_15.0.2.v20210201-0955/jre/bin/server;C:/Users/jeete/Downloads/eclipse-jee-2021-03-R-win32-x86_64/eclipse//plugins/org.eclipse.justj.openjdk.hotspot.jre.full.win32.x86_64_15.0.2.v20210201-0955/jre/bin;C:\Program Files\Common Files\Oracle\Java\javapath;C:\Windows\system32;C:\Windows;C:\Windows\System32\Wbem;C:\Windows\System32\WindowsPowerShell\v1.0\;C:\Windows\System32\OpenSSH\;C:\Program Files\Git\cmd;C:\Program Files\LLVM\bin;C:\Program Files\CMake\bin;C:\Program Files\dotnet\;C:\Program Files\Microsoft SQL Server\120\Tools\Binn\;C:\Program Files\Java\jdk-16\bin;C:\Program Files\Java\jdk-15.0.2\bin;C:\Gradle\gradle-6.8.3\bin;C:\Users\jeete\Downloads\apache-maven-3.8.1-bin\apache-maven-3.8.1\bin;C:\Program Files\MySQL\MySQL Shell 8.0\bin\;C:\Ruby27-x64\bin;C:\Users\jeete\AppData\Local\Microsoft\WindowsApps;C:\Users\jeete\.dotnet\tools;C:\Users\jeete\AppData\Local\GitHubDesktop\bin;C:\Users\jeete\AppData\Local\atom\bin;C:\Users\jeete\Downloads\eclipse-jee-2021-03-R-win32-x86_64\eclipse;;., no libtcnative-1 in java.library.path: C:\Users\jeete\Downloads\eclipse-jee-2021-03-R-win32-x86_64\eclipse\plugins\org.eclipse.justj.openjdk.hotspot.jre.full.win32.x86_64_15.0.2.v20210201-0955\jre\bin;C:\Windows\Sun\Java\bin;C:\Windows\system32;C:\Windows;C:/Users/jeete/Downloads/eclipse-jee-2021-03-R-win32-x86_64/eclipse//plugins/org.eclipse.justj.openjdk.hotspot.jre.full.win32.x86_64_15.0.2.v20210201-0955/jre/bin/server;C:/Users/jeete/Downloads/eclipse-jee-2021-03-R-win32-x86_64/eclipse//plugins/org.eclipse.justj.openjdk.hotspot.jre.full.win32.x86_64_15.0.2.v20210201-0955/jre/bin;C:\Program Files\Common Files\Oracle\Java\javapath;C:\Windows\system32;C:\Windows;C:\Windows\System32\Wbem;C:\Windows\System32\WindowsPowerShell\v1.0\;C:\Windows\System32\OpenSSH\;C:\Program Files\Git\cmd;C:\Program Files\LLVM\bin;C:\Program Files\CMake\bin;C:\Program Files\dotnet\;C:\Program Files\Microsoft SQL Server\120\Tools\Binn\;C:\Program Files\Java\jdk-16\bin;C:\Program Files\Java\jdk-15.0.2\bin;C:\Gradle\gradle-6.8.3\bin;C:\Users\jeete\Downloads\apache-maven-3.8.1-bin\apache-maven-3.8.1\bin;C:\Program Files\MySQL\MySQL Shell 8.0\bin\;C:\Ruby27-x64\bin;C:\Users\jeete\AppData\Local\Microsoft\WindowsApps;C:\Users\jeete\.dotnet\tools;C:\Users\jeete\AppData\Local\GitHubDesktop\bin;C:\Users\jeete\AppData\Local\atom\bin;C:\Users\jeete\Downloads\eclipse-jee-2021-03-R-win32-x86_64\eclipse;;.
	at org.apache.tomcat.jni.Library.<init>(Library.java:102) ~[tomcat-embed-core-9.0.46.jar:9.0.46]
   
   2021-06-08 13:21:43.627 DEBUG 16556 --- [           main] o.apache.tomcat.util.compat.Jre16Compat  : Class not found so assuming code is running on a pre-Java 16 JVM

   java.lang.ClassNotFoundException: **java.net.UnixDomainSocketAddress**
	   at java.base/jdk.internal.loader.BuiltinClassLoader.loadClass(BuiltinClassLoader.java:606) ~[na:na]





    Using **logging.level.edu.ds.ms.retail.catalog2=TRACE** does not cause any exception.

7. moved back to catalog project, since now the exceptions issue is resolved.
8. 
9. 
10. 