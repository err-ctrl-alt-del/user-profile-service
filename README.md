# User Profile Application

##### Minishift Test
---

##### How to run this project?

Running on minishift:
---
Run using the following commands
 
	PS C:\Users\Gerrard\Documents\GitHub\user-profile-service>mvn clean package
	
	PS C:\Users\Gerrard> minishift delete --force
    Removing entries from kubeconfig for cluster: :8443
    Deleting the Minishift VM...
    Minishift VM deleted.
    
	PS C:\Users\Gerrard> minishift start
    -- Starting profile 'minishift'
    -- Check if deprecated options are used ... OK
    -- Checking if https://github.com is reachable ... OK
    -- Checking if requested OpenShift version 'v3.11.0' is valid ... OK
    -- Checking if requested OpenShift version 'v3.11.0' is supported ... OK
    ...
    You are logged in as:
        User:     developer
        Password: <any value>
    
    To login as administrator:
        oc login -u system:admin
    
    
    -- Applying addon 'admin-user':..
    
    
    PS C:\Users\Gerrard> oc delete all -l app=user-profile-service
    
    PS C:\Users\Gerrard> oc new-build registry.access.redhat.com/redhat-openjdk-18/openjdk18-openshift:1.3 --binary=true --name=user-profile-service
    --> Found Docker image 3bb0d23 (14 months old) from registry.access.redhat.com for "registry.access.redhat.com/redhat-openjdk-18/openjdk18-openshift:1.3"
    
        Java Applications
        -----------------
        Platform for building and running plain Java applications (fat-jar and flat classpath)
    
        Tags: builder, java
    
        * An image stream tag will be created as "openjdk18-openshift:1.3" that will track the source image
        * A source build using binary input will be created
          * The resulting image will be pushed to image stream tag "user-profile-service:latest"
          * A binary build was created, use 'start-build --from-dir' to trigger a new build
    
    --> Creating resources with label build=user-profile-service ...
        imagestream.image.openshift.io "openjdk18-openshift" created
        imagestream.image.openshift.io "user-profile-service" created
        buildconfig.build.openshift.io "user-profile-service" created
    --> Success
    
    PS C:\Users\Gerrard> oc start-build user-profile-service --from-dir C:\Users\Gerrard\Documents\GitHub\user-profile-service\target --follow
    Uploading directory "C:\\Users\\Gerrard\\Documents\\GitHub\\user-profile-service\\target" as binary input for the build ...
    ..
    Uploading finished
    build.build.openshift.io/user-profile-service-1 started
    Receiving source from STDIN as archive ...
    Using registry.access.redhat.com/redhat-openjdk-18/openjdk18-openshift@sha256:6c009f430da02bdcff618a7dcd085d7d22547263eeebfb8d6377a4cf6f58769d as the s2i builder image
    ==================================================================
    Starting S2I Java Build .....
    S2I source build with plain binaries detected
    Copying binaries from /tmp/src to /deployments ...
    ... done
    Pushing image 172.30.1.1:5000/myproject/user-profile-service:latest ...
    Pushed 0/6 layers, 2% complete
    Pushed 1/6 layers, 28% complete
    Pushed 2/6 layers, 47% complete
    Pushed 3/6 layers, 72% complete
    Pushed 4/6 layers, 84% complete
    Pushed 5/6 layers, 93% complete
    Pushed 6/6 layers, 100% complete
    Push successful
    
    PS C:\Users\Gerrard> oc new-app user-profile-service
    --> Found image d187286 (58 seconds old) in image stream "myproject/user-profile-service" under tag "latest" for "user-profile-service"
    
        temp.builder.openshift.io/myproject/user-profile-service-1:2c2ac8c2
        -------------------------------------------------------------------
        Platform for building and running plain Java applications (fat-jar and flat classpath)
    
        Tags: builder, java
    
        * This image will be deployed in deployment config "user-profile-service"
        * Ports 8080/tcp, 8443/tcp, 8778/tcp will be load balanced by service "user-profile-service"
          * Other containers can access this service through the hostname "user-profile-service"
    
    --> Creating resources ...
        deploymentconfig.apps.openshift.io "user-profile-service" created
        service "user-profile-service" created
    --> Success
        Application is not exposed. You can expose services to the outside world by executing one or more of the commands below:
         'oc expose svc/user-profile-service'
        Run 'oc status' to view your app.
        
    // Edit Secret mongodb    
    apiVersion: v1
    data:
      DATABASE_ADMIN_PASSWORD: c09hSUlReWhyTjZUOG4zZA==
      DATABASE_NAME: c2FtcGxlZGI=
      DATABASE_PASSWORD: YTQ4ZkoweTJYT2pNVXRWdw==
      DATABASE_USER: dXNlckI0Vg==
    kind: Secret
    metadata:
      annotations:
        template.openshift.io/expose-admin_password: '{.data[''DATABASE_ADMIN_PASSWORD'']}'
        template.openshift.io/expose-database_name: '{.data[''DATABASE_NAME'']}'
        template.openshift.io/expose-password: '{.data[''DATABASE_PASSWORD'']}'
        template.openshift.io/expose-username: '{.data[''DATABASE_USER'']}'
      creationTimestamp: '2019-07-25T07:18:22Z'
      labels:
        app: mongodb-persistent
        template: mongodb-persistent-template
      name: mongodb
      namespace: myproject
      resourceVersion: '8118'
      selfLink: /api/v1/namespaces/myproject/secrets/mongodb
      uid: 62cce907-aeac-11e9-a13b-00155d2fb104
    type: Opaque        
    
    PS C:\Users\Gerrard> oc set env bc/user-profile-service --from="secret/mongodb" --prefix=MONGO_
    buildconfig.build.openshift.io/user-profile-service updated	
	
Test with the following command:
    
    oc expose svc user-profile-service
    route.route.openshift.io/user-profile-service exposed
    
    http://user-profile-service-myproject.172.17.65.202.nip.io/swagger-ui.html