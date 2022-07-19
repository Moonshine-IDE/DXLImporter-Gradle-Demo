# DxlImporterDemo_GradleMinimal

Example project for using Gradle to run DXL Importer scripts.

## Requirements

Environment requirements:
- Gradle (tested with 5.4.1)
- Java 8 (required for Domino API)
- HCL Notes installation (tested with 11.0)


## Using this demo

To try this demo:
1. Setup `gradle.properties` according to the template in [Domino Configuration](#domino-configuration)
2. Run `gradle importAll`

This will import the following agents:
- DemoJavaAgent - example that can be run from the Notes client
TODO:  add more

## Agent Configuration

Configure agents in `agentProperties/agentbuild`.

Configure script libraries in `agentProperties/scriptbuild`

The name of the file (excluding the `.properties extension`) should match the name of the agent.  This name will be used in [Running Tasks](#running-tasks).

TODO:  writeup for the properties.

## Domino Configuration

The following parameters are required:
- notesInstallation:  The path to the local notes installation
- server:  The target Domino server for the agent database
- dbName:  The target name and path for the agent database

You can specify the properties in `gradle.properties`:

	notesInstallation=/Applications/HCL Notes.app/Contents/MacOS/
	server=myserver.mydomain.com
	dbName=path/to/database.nsf

You can also pass it with command line arguments:

	gradle clean importDemoJavaAgent -PnotesInstallation=/Applications/HCL\ Notes.app/Contents/MacOS/ -Pserver=myserver.mydomain.com -PdbName=path/to/database.nsf
	
These configuration types can be used together - command line arguments will take priority in this case.  
[This article](https://tomgregory.com/gradle-project-properties-best-practices/) shows other ways to set the properties.

## Running Tasks

To import all agents, use the `importAll` task:

	gradle importAll
	
To import a single agent or script library, call `import<Name>` where `<Name>` matches the name of the [properties file](#agent-configuration) in `agentProperties`.

	gradle importDemoJavaAgent
	
The `import<Name>` tasks are generated automatically, but you can define your own task if desired.  TODO:  describe how to override the import or jar tasks