rabbitmq.host = "rabbitmq"
rabbitmq.port = "5672"
rabbitmq.username='router'
rabbitmq.password='router'

cytomine.core.url='https://ap-demo-core.labflow.ai'
cytomine.core.publicKey='5e186c6c-4d15-40df-907b-5eddae615cfe'
cytomine.core.privateKey='329f1d39-871b-4064-9238-29eae8443336'

cytomine.software.communication.exchange = "exchangeCommunication"
cytomine.software.communication.queue = "queueCommunication"

cytomine.software.path.softwareSources='/data/softwares/code'
cytomine.software.path.softwareImages='/data/softwares/images'
cytomine.software.path.jobs='/data/jobs'
cytomine.software.sshKeysFile='/root/.ssh/id_rsa'
cytomine.software.descriptorFile = "descriptor.json"

cytomine.software.ssh.maxRetries = 3
cytomine.core.connectionRetries = 20
cytomine.software.allowDockerfileCompilation = true

// In seconds
cytomine.software.repositoryManagerRefreshRate = 3600
cytomine.software.job.logRefreshRate = 15
cytomine.software.pullingCheckRefreshRate = 20
cytomine.software.pullingCheckTimeout = 1800
cytomine.core.connectionRefreshRate = 30

cytomine.software.github.username=""
cytomine.software.github.token=""

cytomine.software.dockerhub.username=""
cytomine.software.dockerhub.password=""

