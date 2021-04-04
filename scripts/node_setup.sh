#!/usr/bin/env bash

# Constants
TOMCAT_DOWNLOAD_URL="https://archive.apache.org/dist/tomcat/tomcat-9/v9.0.20/bin/apache-tomcat-9.0.20.zip"
INTALL_DIR="/mydata"

# Installing Apache-Ant.
sudo apt-get update && sudo apt-get install -y ant

# Installing Java.
sudo apt-get install -y default-jdk

# Building the jar.
ant war

# Installing and starting up tomcat.
cd ${HOME} && wget ${TOMCAT_DOWNLOAD_URL}
unzip apache-tomcat-9.0.20.zip && mv apache-tomcat-9.0.20 apache-tomcat
chmod +x apache-tomcat/bin/*
bash apache-tomcat/bin/startup.sh

# Deploying the web app.
cp build/war/StreamData.war ${HOME}/apache-tomcat/webapps

# Installing collectl
cd ${INSTALL_DIR} && wget --header='Host: versaweb.dl.sourceforge.net' --header='User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 Safari/537.36' --header='Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9' --header='Accept-Language: en-US,en;q=0.9' --header='Referer: https://sourceforge.net/projects/collectl/files/latest/download' --header='Cookie: _ga=GA1.2.986096645.1596489868; __adroll_fpc=e0ba0b947167593e21a0a1531d172e84-1596489870226; __gads=ID=52e553ff1f281c26:T=1596489868:S=ALNI_MZZR8t8kQAU_8CLtePpwzxbHFn-SQ; _fbp=fb.1.1596489871422.1495302713; _gid=GA1.2.34635475.1599078038; __ar_v4=%7C3QEU55AVURGVNFYKGPRLHU%3A20200902%3A1%7CEPGGWMNOENDCJMRYE2IIFV%3A20200902%3A1%7COLCQG7YFPFB7ZDDI7VV6SN%3A20200902%3A1' --header='Connection: keep-alive' 'https://versaweb.dl.sourceforge.net/project/collectl/collectl/collectl-4.3.1/collectl-4.3.1.src.tar.gz' -c -O collectl-4.3.1.src.tar.gz
tar -xzvf collectl-4.3.1.src.tar.gz && cd collectl-4.3.1 && sudo ./INSTALL

# Starting collectl.
sudo collectl -file -P -F0 -oz -f /var/log/collectl &>/dev/null &