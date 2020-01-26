# pr-metaservice
This project is a spring-boot project.
Requirements: Maven, Java 11

## Installation of Fuseki Semantic Web Services

The semantic web services used for this demo are all run by one Apache Jena Fuseki instance in a docker container. To get started, you need to have Docker installed and working on your system.

### Pull Fuseki Image

```console
docker pull stain/jena-fuseki
```

### Start container

```console
docker run -p 4000:3000 -p 4040:3030 -e ADMIN_PASSWORD=your_password -d --name sparqlservice stain/jena-fuseki
```

### Login and Create datasets

[fuseki_ip:4040](http://localhost:4040)
Username: **admin**
Password: **your_password**

Go to **Manage Datasets** and click **Add new Dataset**, then add the following datasets as **Persistent – dataset will persist across Fuseki restarts**:
- audi
- joescarparts
- genericsupply

### Upload data

For each dataset, click on **Upload Data** and choose the corresponding RDF File.

## The Sparql endpoints can now be queried using the endpoint fuseki_ip:4040/audi/sparql?query={{query}}


## Troubleshooting

If you are using Windows, the IP Address of your docker container might be difficult to find. Please refer to this [Guide](https://thispointer.com/how-to-get-ip-address-of-running-docker-container-from-host-using-inspect-command/)

