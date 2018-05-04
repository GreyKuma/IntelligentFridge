\echo \conninfo
\echo
\echo -n 'client encoding: '\encoding
\echo

SET client_min_messages = ERROR;
\set user ***REMOVED***
\set password '\'***REMOVED***\''
\set database FridgeTestDatabase
\set promptvar ''

\prompt 'DROP USER [':user'] and DROP DATABASE [':database'] if existing (\\q or Ctrl-C to abort)?' promptvar
:promptvar

DROP DATABASE IF EXISTS :database;
DROP USER IF EXISTS :user;

\echo -------------------------------------
\echo passwort for user :user = :password
\echo -------------------------------------
\echo

\prompt 'CREATE USER [':user'] and DATABASE [':database'] (\\q or Ctrl-C to abort)?' promptvar
:promptvar

DROP DATABASE IF EXISTS :database;
DROP USER IF EXISTS :user;

CREATE USER :user WITH PASSWORD :password;
CREATE DATABASE :database WITH OWNER :user ENCODING 'UTF8';
\c :database :user

