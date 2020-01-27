# Sparql-Query-Liste:


# GenericSupply

## Abfrage der Automarken

```sparql
prefix gs: <http://www.jku.at/dke/praktikumdke/gruppe6/ersatzteilhersteller1#>

SELECT ?brand
WHERE {
  ?brand a gs:Brand
}
```

## Abfrage der Modelle pro Marke
```sparql
prefix gs: <http://www.jku.at/dke/praktikumdke/gruppe6/ersatzteilhersteller1#>

SELECT ?model
WHERE {
  ?model gs:hasBrand gs:Audi.
  ?model a gs:Model.
}
```

## Abfrage der Teile pro Modell inkl. Preise
```sparql
prefix gs: <http://www.jku.at/dke/praktikumdke/gruppe6/ersatzteilhersteller1#>

SELECT DISTINCT ?part ?price
WHERE {
  ?part a gs:Product.
  ?part gs:hasPrice ?price.
  ?part gs:fitsFor ?model.
  ?model gs:hasBrand gs:Audi.
}
```

# JoesCarParts

## Abfrage der Automarken

```sparql
prefix jcp: <http://www.semanticweb.org/johannes/ontologies/2019/9/untitled-ontology-9#>

SELECT DISTINCT ?brand
WHERE {
  ?brand <http://www.w3.org/2000/01/rdf-schema#subClassOf> jcp:car.
}
```

## Abfrage der Modelle pro Automarke

**Alle Models**
```sparql
prefix jcp: <http://www.semanticweb.org/johannes/ontologies/2019/9/untitled-ontology-9#>

SELECT DISTINCT ?model
WHERE {
  {SELECT distinct ?brand WHERE {?brand <http://www.w3.org/2000/01/rdf-schema#subClassOf> jcp:car.}}
  ?model <http://www.w3.org/2000/01/rdf-schema#subClassOf> ?brand.
}
```

**Models pro Marke**
```sparql
prefix jcp: <http://www.semanticweb.org/johannes/ontologies/2019/9/untitled-ontology-9#>

SELECT DISTINCT ?model
WHERE {
  ?model <http://www.w3.org/2000/01/rdf-schema#subClassOf> jcp:Audi.
}
```

## Abfrage der Teile pro Modell inkl. Preise

```sparql
prefix jcp: <http://www.semanticweb.org/johannes/ontologies/2019/9/untitled-ontology-9#>

SELECT DISTINCT ?part ?price
WHERE {
  ?part jcp:belongsTo jcp:A4.
  ?part jcp:hasPrice ?price.
}
```

# GenericSupply

## Abfrage der Automarken

```sparql
prefix gs: <http://www.jku.at/dke/praktikumdke/gruppe6/ersatzteilhersteller1#>

SELECT ?brand
WHERE {
  ?brand a gs:Brand
}
```

## Abfrage der Modelle pro Marke
```sparql
prefix gs: <http://www.jku.at/dke/praktikumdke/gruppe6/ersatzteilhersteller1#>

SELECT ?model
WHERE {
  ?model gs:hasBrand gs:Audi.
  ?model a gs:Model.
}
```

## Abfrage der Teile pro Modell inkl. Preise
```sparql
prefix gs: <http://www.jku.at/dke/praktikumdke/gruppe6/ersatzteilhersteller1#>

SELECT DISTINCT ?part ?price
WHERE {
  ?part a gs:Product.
  ?part gs:hasPrice ?price.
  ?part gs:fitsFor ?model.
  ?model gs:hasBrand gs:Audi.
}
```

# Audi

## Abfrage der Automarken

**Nur Audi**

## Abfrage der Modelle pro Automarke

```sparql
prefix audi: <http://www.jku.at/dke/praktikumdke/gruppe6/autohersteller1_audi#>

SELECT ?model ?modelstring
WHERE {
  ?model a audi:Model.
  BIND(strafter(strafter(STR(?model), "#"), "_") as ?modelstring).
}
```

## Abfrage der Teile pro Modell inkl. Preise

```sparql
prefix audi: <http://www.jku.at/dke/praktikumdke/gruppe6/autohersteller1_audi#>
prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>

SELECT ?part ?partstring ?category
WHERE {
  ?part a ?category.
  ?category rdfs:subClassOf* audi:Part.
  BIND(strafter(strafter(STR(?part), "#"), "_") as ?partstring).
}
```