Pro pou�it� aplikace je nutn� vytvo�it si sv� vlastn� credentials pro mo�nost pou�it� dat z Twitteru.

Aplikace pou��v� tzv. Application-Only Authentication a proto je pot�eba vygenerovat si sv� vlastn� credentials.
V�ce informac� zde: https://dev.twitter.com/oauth

Po vygenerov�n� si vlastn�ch credentials je pot�eba vepsat
tato credentials do t��dy: cz.ucl.recom.config.TwitterConfiguration

Nyn� se ud�l� build zdrojov�ho k�du pomoc� Maven: mvn clean install

Vygenerovan� EAR je k nalezen� ve slo�ce: recommendation/recommendation-ear/target

Tento EAR je nyn� mo�n� nasadit na aplika�n� server.