# SMS Service challenge
Ziel dieser challenge ist es, unsere SMS HTTP Api in einen Spring boot service einzubinden und einen HTTP API endpoint bereitzustellen, mit dem man Nachrichten an bestimmte User (Auswahlkriterium : Email) via SMS versenden kann.

Basis ist ein Spring Boot service (spring-challenge). Dieses einfache Maven Projekt basiert auf Spring boot Web und Spring boot JPA mit einer lokalen H2 Datenbank.

Die H2 Datenbank wird beim start automatisch mit einer user Tabelle neu initialisiert (im ./db Verzeichnis) wenn in der application.yml spring.sql.init.mode auf 'always' steht. Falls dies nicht erwünscht ist, einfach auf 'never' setzen

Während der Service läuft kann man auch über eine Web Console unter http://localhost:8088/administration/db-console/ auf die DB zugreifen. (url, username und password, siehe application.yml - spring.datasourse)
 
Die SMS API Dokumentation ist unter https://sms.vanso.com/api-docs.html verfügbar. 
Zugangsdaten für die API werden geteilt.

Restriktionen der SMS API:
Absender muss immer "TEST" sein
Der maximale Durchsatz ist auf1 SMS pro Sekunde begrenzt
Es werden nur eine begrenzte Anzahl von SMS freigeschaltet - zum testen von Durchsatz bitte nur User mit id 3 nutzen.

Um dein Ergebnis zu teilen, erstelle bitte einen Commit auf dem Branch den wir die zusammen mit den SMS API Zugangsdaten mitteilen.
