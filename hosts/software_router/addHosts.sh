echo "#Custom adding" >> /etc/hosts
echo "$(route -n | awk '/UG[ \t]/{print $2}')       ap-demo-core.labflow.ai" >> /etc/hosts
echo "$(route -n | awk '/UG[ \t]/{print $2}')       ap-demo-ims.labflow.ai" >> /etc/hosts
echo "$(route -n | awk '/UG[ \t]/{print $2}')       ap-demo-ims2.labflow.ai" >> /etc/hosts
echo "$(route -n | awk '/UG[ \t]/{print $2}')       ap-demo-upload.labflow.ai" >> /etc/hosts
