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

Optional configuration properties:
- `importLogMode`:  Set to `debug` to see additional debugging output from the DXL Importer process.

## Running Tasks

To import all agents, use the `importAll` task:

	gradle importAll
	
To import a single agent or script library, call `import<Name>` where `<Name>` matches the name of the [properties file](#agent-configuration) in `agentProperties`.

	gradle importDemoJavaAgent
	
The `import<Name>` tasks are generated automatically, but you can define your own task if desired.  TODO:  describe how to override the import or jar tasks





## GitHub Actions

This template includes an example GitHub action.  This is designed to support an automated development workflow based on [Super.Human.Installer](https://superhumaninstaller.com/) Domino instances.
1. Make changes to an agent
2. Commit
3. The GitHub Action will:
    1. Create an empty database
    2. Import any forms, views, documents, etc that you configure
    3. Import the agents using `gradle clean importAll`
4. You can then test the agent update on your local machine

### Setup

TODO:  Fill in these instructions

1. Create a copy of this repository

2. Create an SHI instance.  You can setup a self-hosted runner for this instance on creation (TODO), or install it later

3. In your repository, open `Settings > Secrets and variables > Actions` and set these variables and secrets

Variable                    | Notes
----------------------------|--------------------
`TEST_SERVER`               | The Domino server name.  Example:  domino-1.acme.com/ACME
`TEST_DATABASE`             | The test database name and path.
`TEST_DOMINO_INSTALLATION`  | The path to Domino.  Should be:  `/opt/hcl/domino/notes/latest/linux/`


Secret                      | Notes
----------------------------|--------------------
`TEST_PASSWORD`             | This will be `password` unless you changed it

4. Commit a change to your repository, or trigger the build manually from the Actions tab

5. Test your agent on your instance:  `https://%server%/%TEST_DATABASE%/%agent_name%?OpenAgent`

For example:  `https://domino-1.acme.com/Test.nsf/HelloWorld?OpenAgent`
