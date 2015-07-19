Pro použití aplikace je nutné vytvoøit si své vlastní credentials pro možnost použití dat z Twitteru.

Aplikace používá tzv. Application-Only Authentication a proto je potøeba vygenerovat si svá vlastní credentials.
Více informací zde: https://dev.twitter.com/oauth

Po vygenerování si vlastních credentials je potøeba vepsat
tato credentials do tøídy: cz.ucl.recom.config.TwitterConfiguration

Nyní se udìlá build zdrojového kódu pomocí Maven: mvn clean install

Vygenerovaný EAR je k nalezení ve složce: recommendation/recommendation-ear/target

Tento EAR je nyní možné nasadit na aplikaèní server.