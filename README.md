# DxlImporterDemo_GradleMinimal

This is a template repository for writing Java agents based on the HCL Domino® API.  It has two main components:
- DXL Importer - used to import the agents from a Java Gradle project into a database
- GitHub Action - Used to generate at test environment for the agents in a 

## Requirements

Environment requirements:
- Gradle (tested with 5.4.1)
- Java 8 (required for Domino API)
- HCL Notes® or Domino® installation.
- [Super.Human.Installer](https://www.superhumaninstaller.com/) 1.7.0 or alter


## DXL Importer

### Using this demo

To try this demo:
1. Setup `gradle.properties` according to the template in [Domino Configuration](#domino-configuration)
2. Run `gradle importAll`

The following example agents are configured in the template
- `DemoJavaAgent` - An agent that runs in the Notes client
- `TestWeb` - An agent that can be called from the browser
- `WQS_Placeholder` - A WebQuerySave agent that can be called from a form

You can also find an example script library, `DemoScriptLibrary`

### Agent Configuration

Configure agents in `agentProperties/agentbuild`.

Configure script libraries in `agentProperties/scriptbuild`

The name of the file (excluding the `.properties extension`) should match the name of the agent.  This name will be used in [Running Tasks](#running-tasks).

TODO:  writeup or link the property documentation

### Domino Configuration

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

### Running Tasks

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

#### Create and configure the repository

For a new repository, you can [create your repository from this template](https://docs.github.com/en/repositories/creating-and-managing-repositories/creating-a-repository-from-a-template)

#### Setup a Self-hosted Runner

The provided GitHub action is expected to run in a Vagrant instance created with Super.Human.Installer.  When creating the instance, choose:
- Service:  HCL Standalone Provisioner
- Vagrant Provisioner:  HCL Standalone Provisioner v0.1.24 (or later)

You will then need to configure the self-hosted runner with one of three methods.

##### Configure as part of SHI instance

TODO: Update SHI instance before initial creation.

You will need to create a [Github fine-tuned access token](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens#creating-a-fine-grained-personal-access-token).  The required permissions are documented [here](https://github.com/STARTcloud/startcloud_roles/blob/main/roles/git_runner/README.md#github-token-permissions)
- Organization Runners: Organization permissions → Self-hosted runners → Read and Write access
- Repository Runners: Repository permissions → Administration → Read and Write access


##### Configure with Ansible

You can use the same provisioners above to create a runner in an existing instance.  See the full documentation [here](https://github.com/STARTcloud/startcloud_roles/blob/main/roles/git_runner/README.md).

```
ansible localhost -m include_role -a name=startcloud.startcloud_roles.git_runner \
  -e git_runner_github_token=github_pat_TOKEN \
  -e git_runner_org=STARTcloud \
  -e git_runner_name=my-runner \
  -e git_runner_user=java_user \
  -e git_runner_dir=/home/java_user/my-runner \
  -e "git_runner_labels=['self-hosted','super.human.installer']" \
  -e git_runner_ephemeral=false \
  -e git_runner_version=latest \
  --become
```

The `github_pat_TOKEN` is GitHub fine-tuned access token following the same requirements as as above.


##### Configure Manually

For an existing SHI instance, you may find it easier to [create the self-hosted runner through GitHub](https://docs.github.com/en/actions/how-tos/manage-runners/self-hosted-runners/add-runners).  This avoids the need for a personal access token

Some Requirements:
- The self-hosted runner needs to run as `java_user`:  `sudo su - java_user`
- You will need to add at least these labels.  If you forget them, they can be added later in the GitHub interface:  `self-hosted,super.human.installer`


#### Setup Environment

 In your repository, open `Settings > Secrets and variables > Actions` and set these variables and secrets

Variable                    | Notes
----------------------------|--------------------
`TEST_SERVER`               | The Domino server name.  Example:  domino-1.acme.com/ACME
`TEST_DATABASE`             | The test database name and path.
`TEST_DOMINO_INSTALLATION`  | The path to Domino.  Should be:  `/opt/hcl/domino/notes/latest/linux/`


Secret                      | Notes
----------------------------|--------------------
`TEST_PASSWORD`             | This will be `password` unless you changed it


### Usage

Once you are fully configured, you should be able to test your code like this:
1. Commit a change to your repository, or trigger the build manually from the Actions tab
2. Test your agent on your instance:  `https://%server%/%TEST_DATABASE%/%agent_name%?OpenAgent`.  Example:  `https://domino-1.acme.com/Test.nsf/HelloWorld?OpenAgent`
