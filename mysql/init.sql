create database if not exists url_shortner_db default character set utf8;
grant ALTER, CREATE, DELETE, DROP, INDEX, INSERT, SELECT, UPDATE, DELETE,REFERENCES  on url_shortner_db.* to url_shortner_admin@'%' identified by 'url_shortner_pw';
grant DELETE, INSERT, SELECT, UPDATE on url_shortner_db.* to url_shortner_user@'%' identified by 'url_shortner_user_pw';
