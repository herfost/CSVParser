# CSVParser
Parser per CSV: scrittura e lettura  di oggetti _JavaBean_ in file `.csv`.

## Requisiti
L'oggetto deve essere un rispettare le seguenti condizioni:
- deve essere  [JavaBean](https://it.wikipedia.org/wiki/JavaBean)
- l'ordine di apparizione degli attributi deve essere identico tra la classe dell'oggetto e l'intestazione dei campi nel file `.csv`
- il primo attributo della classe dell'oggetto deve essere `serialVersionUID`
- La prima riga del file `.csv` deve indicare il nome dei campi e deve essere seguita da una [`CRLF`](https://developer.mozilla.org/en-US/docs/Glossary/CRLF?retiredLocale=it)
- Importare il file [CSVParser.jar](dist/CSVParser.jar)

### Esempio di utilizzo
#### Scrittura su file
```java 
public static String csvPath = "./points.csv";
public static String separator = ",";
public static void example() {
    Point point = new Point(0, 0);
    CSVParser.writeObjectToCSV(Point.class, point, csvPath, separator);
}
```
#### Lettura da file
```java 
public static String csvPath = "./points.csv";
public static String separator = ",";
public static void example() {    
    List<Object> points = CSVParser.readFromCSV(Point.class, csvPath, separator);
}
```
