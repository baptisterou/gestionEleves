CREATE TABLE ELEVE(
   IdEleve INT,
   NomEleve VARCHAR(50),
   PrenomEleve VARCHAR(50),
   DateNaissEleve DATE,
   PRIMARY KEY(IdEleve)
);

CREATE TABLE CLASSE(
   IdClasse INT,
   NomClasse VARCHAR(25),
   NiveauClasse VARCHAR(15),
   AnneeScolaire VARCHAR(10),
   PRIMARY KEY(IdClasse)
);

CREATE TABLE MATIERE(
   IdMatiere INT,
   IntituleMat VARCHAR(50),
   PRIMARY KEY(IdMatiere)
);

CREATE TABLE NOTE(
   IdNote INT,
   DateNote DATE,
   Coefficient DECIMAL(2,2),
   IdMatiere INT NOT NULL,
   IdEleve INT NOT NULL,
   PRIMARY KEY(IdNote),
   FOREIGN KEY(IdMatiere) REFERENCES MATIERE(IdMatiere),
   FOREIGN KEY(IdEleve) REFERENCES ELEVE(IdEleve)
);

CREATE TABLE BULLETIN(
   IdBulletin INT,
   Trimestre INT,
   Annee DATE,
   IdEleve INT NOT NULL,
   PRIMARY KEY(IdBulletin),
   FOREIGN KEY(IdEleve) REFERENCES ELEVE(IdEleve)
);

CREATE TABLE UTILISATEUR(
   IdUtilisateur INT,
   NomUtil VARCHAR(50),
   PrenomUtil VARCHAR(50),
   DateNaiss DATE,
   MotDePasse VARCHAR(25),
   NumTel VARCHAR(15),
   Mail VARCHAR(50),
   PRIMARY KEY(IdUtilisateur)
);

CREATE TABLE ADMINISTRATEUR(
   IdAdmin INT,
   IdUtilisateur INT NOT NULL,
   PRIMARY KEY(IdAdmin),
   UNIQUE(IdUtilisateur),
   FOREIGN KEY(IdUtilisateur) REFERENCES UTILISATEUR(IdUtilisateur)
);

CREATE TABLE INSCRIPTION(
   IdInscription INT,
   DateInscription DATE,
   IdAdmin INT NOT NULL,
   IdClasse INT NOT NULL,
   IdEleve INT NOT NULL,
   PRIMARY KEY(IdInscription),
   FOREIGN KEY(IdAdmin) REFERENCES ADMINISTRATEUR(IdAdmin),
   FOREIGN KEY(IdClasse) REFERENCES CLASSE(IdClasse),
   FOREIGN KEY(IdEleve) REFERENCES ELEVE(IdEleve)
);

CREATE TABLE ENSEIGNANT(
   IdEnseignant INT,
   IdUtilisateur INT NOT NULL,
   PRIMARY KEY(IdEnseignant),
   UNIQUE(IdUtilisateur),
   FOREIGN KEY(IdUtilisateur) REFERENCES UTILISATEUR(IdUtilisateur)
);

CREATE TABLE RESPONSABLE(
   IdResponsable INT,
   IdUtilisateur INT NOT NULL,
   PRIMARY KEY(IdResponsable),
   UNIQUE(IdUtilisateur),
   FOREIGN KEY(IdUtilisateur) REFERENCES UTILISATEUR(IdUtilisateur)
);

CREATE TABLE ENSEIGNE(
   IdEleve INT,
   IdEnseignant INT,
   IdMatiere INT,
   PRIMARY KEY(IdEleve, IdEnseignant, IdMatiere),
   FOREIGN KEY(IdEleve) REFERENCES ELEVE(IdEleve),
   FOREIGN KEY(IdEnseignant) REFERENCES ENSEIGNANT(IdEnseignant),
   FOREIGN KEY(IdMatiere) REFERENCES MATIERE(IdMatiere)
);

CREATE TABLE REPRESENTE(
   IdEleve INT,
   IdResponsable INT,
   PRIMARY KEY(IdEleve, IdResponsable),
   FOREIGN KEY(IdEleve) REFERENCES ELEVE(IdEleve),
   FOREIGN KEY(IdResponsable) REFERENCES RESPONSABLE(IdResponsable)
);
