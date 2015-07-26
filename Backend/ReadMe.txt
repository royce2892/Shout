1.Install mongodb from https://www.mongodb.org/
2.Go to the directory in cmd and install all dependencies by command 
	$npm install
3.start server by running start.js
	$node start.js
4.Test using http://localhost:3000/find?ln=en&topic=w&pg=0
ln =language
 {en,hi,ml,ta}
topic ={'','s','b','n','w','e','h','kl','awkl','tn','sn','tc','snc','m','ir'}
''-with no topic, gives top stories
w - world
n - india
e - entertainment
b - business
s - sports
h - more top stories
kl - Kerala news, only with malayalam
awkl- Gulf news, only with malayalam
tn - Tamilnadu news, only with tamil
sn - Srilankan news, only with tamil
tc - tech news, only with english
snc- science news, only with english
m - helth news, only with english
ir -spotlight, only with english
pg = page

Automatic Deployment to Amazon Ec2 Enabled for this repo.. Any commit to master branch will be deployed to AWS.
And you can see the live version at 
Elastic IP
http://52.74.222.16:3000/find?ln=en&topic=n&pg=0
http://dev-news-engine.indimakes.com:3000/find?ln=en&topic=n&pg=0