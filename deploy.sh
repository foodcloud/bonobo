git init
git add .
git commit -m 'init'
echo 'web: target/start -Dhttp.port=${PORT} ${JAVA_OPTS}' > procfile
heroku create --stack cedar
git push heroku master
heroku ps
heroku logs
heroku open