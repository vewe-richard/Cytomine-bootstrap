dataSource.url='jdbc:postgresql://postgresql:5432/docker'
dataSource.username='docker'
dataSource.password='docker'

cytomine.customUI.global = [
        dashboard: ["ALL"],
        search : ["ROLE_ADMIN"],
        project: ["ALL"],
        ontology: ["ROLE_ADMIN"],
        storage : ["ROLE_USER","ROLE_ADMIN"],
        activity : ["ALL"],
        feedback : ["ROLE_USER","ROLE_ADMIN"],
        explore : ["ROLE_USER","ROLE_ADMIN"],
        admin : ["ROLE_ADMIN"],
        help : ["ALL"]
]


grails.serverURL='https://ap-demo-core.labflow.ai'
grails.imageServerURL=['https://ap-demo-ims.labflow.ai','https://ap-demo-ims2.labflow.ai']
grails.uploadURL='https://ap-demo-upload.labflow.ai'

storage_buffer='/data/images/_buffer'
storage_path='/data/images'

grails.adminPassword='5cdf435b-522e-45af-9a8c-a4bc660de58b'
grails.adminPrivateKey='a20f8d5f-10b6-4922-888f-d5e1d058cb54'
grails.adminPublicKey='5bf0642c-682e-4f02-b229-e4d2e5a98eaa'
grails.superAdminPrivateKey='c2d4ac53-8c74-41c1-abc3-4971946922cf'
grails.superAdminPublicKey='244f9baa-7aa3-491c-bb24-772b907e3c67'
grails.ImageServerPrivateKey='fa8cd18c-6d4e-4aa2-a2d6-0885f99b59d9'
grails.ImageServerPublicKey='076f228b-062d-4d2a-8b2c-926cbaff4b94'
grails.rabbitMQPrivateKey='329f1d39-871b-4064-9238-29eae8443336'
grails.rabbitMQPublicKey='5e186c6c-4d15-40df-907b-5eddae615cfe'

grails.notification.email='your.email@gmail.com'
grails.notification.password='passwd'
grails.notification.smtp.host='smtp.gmail.com'
grails.notification.smtp.port='587'
grails.admin.email='info@cytomine.coop'

grails.mongo.host = 'mongodb'
grails.mongo.options.connectionsPerHost=10
grails.mongo.options.threadsAllowedToBlockForConnectionMultiplier=5

grails.messageBrokerServerURL='rabbitmq:5672'

grails.serverID='1d263a47-873f-452e-8d47-6dc054f0ed09'

grails.plugin.springsecurity.successHandler.ajaxSuccessUrl = "${grails.serverURL}/login/ajaxSuccess"
grails.plugin.springsecurity.failureHandler.ajaxAuthFailUrl = "${grails.serverURL}/login/authfail?ajax=true"
