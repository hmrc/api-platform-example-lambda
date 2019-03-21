yum install -y java-1.8.0-openjdk-devel
curl https://bintray.com/sbt/rpm/rpm > /etc/yum.repos.d/bintray-sbt-rpm.repo
yum install -y sbt
sbt assembly
