CREATE TABLE ELEVE
(
    IdEleve        INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    NomEleve       VARCHAR(50),
    PrenomEleve    VARCHAR(50),
    DateNaissEleve DATE
);

CREATE TABLE CLASSE
(
    IdClasse      INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    NomClasse     VARCHAR(25),
    NiveauClasse  VARCHAR(15),
    AnneeScolaire VARCHAR(10)
);

CREATE TABLE MATIERE
(
    IdMatiere   INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    IntituleMat VARCHAR(50)
);

CREATE TABLE NOTE
(
    IdNote      INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    DateNote    DATE,
    Coefficient NUMERIC(2, 2),
    IdMatiere   INTEGER NOT NULL,
    IdEleve     INTEGER NOT NULL,
    FOREIGN KEY (IdMatiere) REFERENCES MATIERE (IdMatiere),
    FOREIGN KEY (IdEleve) REFERENCES ELEVE (IdEleve)
);

CREATE TABLE BULLETIN
(
    IdBulletin INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    Trimestre  INTEGER,
    Annee      DATE,
    IdEleve    INTEGER NOT NULL,
    FOREIGN KEY (IdEleve) REFERENCES ELEVE (IdEleve)
);

CREATE TABLE ROLE
(
    IdRole  INTEGER,
    Libelle VARCHAR(50),
    PRIMARY KEY (IdRole)
);

CREATE TABLE UTILISATEUR
(
    idUtilisateur INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    NomUtil       VARCHAR(50),
    PrenomUtil    VARCHAR(50),
    DateNaiss     DATE,
    MotDePasse    VARCHAR(25),
    NumTel        VARCHAR(15),
    Mail          VARCHAR(50),
    IdRole        INTEGER NOT NULL,
    FOREIGN KEY (IdRole) REFERENCES ROLE (IdRole)
);

CREATE TABLE ENSEIGNE
(
    IdClasse                 INTEGER,
    IdMatiere                INTEGER,
    idUtilisateur_Enseignant INTEGER,
    PRIMARY KEY (IdClasse, IdMatiere, idUtilisateur_Enseignant),
    FOREIGN KEY (IdClasse) REFERENCES CLASSE (IdClasse),
    FOREIGN KEY (IdMatiere) REFERENCES MATIERE (IdMatiere),
    FOREIGN KEY (idUtilisateur_Enseignant) REFERENCES UTILISATEUR (idUtilisateur)
);

CREATE TABLE REPRESENTE
(
    IdEleve                    INTEGER,
    idUtilisateur_Representant INTEGER,
    PRIMARY KEY (IdEleve, idUtilisateur_Representant),
    FOREIGN KEY (IdEleve) REFERENCES ELEVE (IdEleve),
    FOREIGN KEY (idUtilisateur_Representant) REFERENCES UTILISATEUR (idUtilisateur)
);

CREATE TABLE INSCRIRE
(
    IdEleve                      INTEGER,
    idUtilisateur_Administrateur INTEGER,
    DateInscrip                  DATE,
    PRIMARY KEY (IdEleve, idUtilisateur_Administrateur),
    FOREIGN KEY (IdEleve) REFERENCES ELEVE (IdEleve),
    FOREIGN KEY (idUtilisateur_Administrateur) REFERENCES UTILISATEUR (idUtilisateur)
);

CREATE TABLE COMPOSE
(
    IdEleve  INTEGER,
    IdClasse INTEGER,
    PRIMARY KEY (IdEleve, IdClasse),
    FOREIGN KEY (IdEleve) REFERENCES ELEVE (IdEleve),
    FOREIGN KEY (IdClasse) REFERENCES CLASSE (IdClasse)
);
