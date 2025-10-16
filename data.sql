Sub Create_Tables()

DoCmd.RunSQL "CREATE TABLE ELEVE(" & _
   "IdEleve INT," & _
   "NomEleve VARCHAR(50)," & _
   "PrenomEleve VARCHAR(50)," & _
   "DateNaissEleve DATE," & _
   "PRIMARY KEY(IdEleve)" & _
");"   

DoCmd.RunSQL "CREATE TABLE CLASSE(" & _
   "IdClasse INT," & _
   "NomClasse VARCHAR(25)," & _
   "NiveauClasse VARCHAR(15)," & _
   "AnneeScolaire VARCHAR(10)," & _
   "PRIMARY KEY(IdClasse)" & _
");"   

DoCmd.RunSQL "CREATE TABLE MATIERE(" & _
   "IdMatiere INT," & _
   "IntituleMat VARCHAR(50)," & _
   "PRIMARY KEY(IdMatiere)" & _
");"   

DoCmd.RunSQL "CREATE TABLE NOTE(" & _
   "IdNote INT," & _
   "DateNote DATE," & _
   "Coefficient DECIMAL(2,2)," & _
   "IdMatiere INT NOT NULL," & _
   "IdEleve INT NOT NULL," & _
   "PRIMARY KEY(IdNote)," & _
   "FOREIGN KEY(IdMatiere) REFERENCES MATIERE(IdMatiere)," & _
   "FOREIGN KEY(IdEleve) REFERENCES ELEVE(IdEleve)" & _
");"   

DoCmd.RunSQL "CREATE TABLE BULLETIN(" & _
   "IdBulletin INT," & _
   "Trimestre INT," & _
   "Annee DATE," & _
   "IdEleve INT NOT NULL," & _
   "PRIMARY KEY(IdBulletin)," & _
   "FOREIGN KEY(IdEleve) REFERENCES ELEVE(IdEleve)" & _
");"   

DoCmd.RunSQL "CREATE TABLE UTILISATEUR(" & _
   "IdUtilisateur INT," & _
   "NomUtil VARCHAR(50)," & _
   "PrenomUtil VARCHAR(50)," & _
   "DateNaiss DATE," & _
   "MotDePasse VARCHAR(25)," & _
   "NumTel VARCHAR(15)," & _
   "Mail VARCHAR(50)," & _
   "PRIMARY KEY(IdUtilisateur)" & _
");"   

DoCmd.RunSQL "CREATE TABLE ADMINISTRATEUR(" & _
   "IdAdmin INT," & _
   "Role VARCHAR(20)," & _
   "IdUtilisateur INT NOT NULL," & _
   "PRIMARY KEY(IdAdmin)," & _
   "UNIQUE(IdUtilisateur)," & _
   "FOREIGN KEY(IdUtilisateur) REFERENCES UTILISATEUR(IdUtilisateur)" & _
");"   

DoCmd.RunSQL "CREATE TABLE ENSEIGNANT(" & _
   "IdEnseignant INT," & _
   "Role VARCHAR(20)," & _
   "IdUtilisateur INT NOT NULL," & _
   "PRIMARY KEY(IdEnseignant)," & _
   "UNIQUE(IdUtilisateur)," & _
   "FOREIGN KEY(IdUtilisateur) REFERENCES UTILISATEUR(IdUtilisateur)" & _
");"   

DoCmd.RunSQL "CREATE TABLE RESPONSABLE(" & _
   "IdResponsable INT," & _
   "Role VARCHAR(20)," & _
   "IdUtilisateur INT NOT NULL," & _
   "PRIMARY KEY(IdResponsable)," & _
   "UNIQUE(IdUtilisateur)," & _
   "FOREIGN KEY(IdUtilisateur) REFERENCES UTILISATEUR(IdUtilisateur)" & _
");"   

DoCmd.RunSQL "CREATE TABLE ENSEIGNE(" & _
   "IdClasse INT," & _
   "IdEnseignant INT," & _
   "IdMatiere INT," & _
   "PRIMARY KEY(IdClasse, IdEnseignant, IdMatiere)," & _
   "FOREIGN KEY(IdClasse) REFERENCES CLASSE(IdClasse)," & _
   "FOREIGN KEY(IdEnseignant) REFERENCES ENSEIGNANT(IdEnseignant)," & _
   "FOREIGN KEY(IdMatiere) REFERENCES MATIERE(IdMatiere)" & _
");"   

DoCmd.RunSQL "CREATE TABLE REPRESENTE(" & _
   "IdEleve INT," & _
   "IdResponsable INT," & _
   "PRIMARY KEY(IdEleve, IdResponsable)," & _
   "FOREIGN KEY(IdEleve) REFERENCES ELEVE(IdEleve)," & _
   "FOREIGN KEY(IdResponsable) REFERENCES RESPONSABLE(IdResponsable)" & _
");"   

DoCmd.RunSQL "CREATE TABLE INSCRIRE(" & _
   "IdEleve INT," & _
   "IdAdmin INT," & _
   "DateInscrip DATE," & _
   "PRIMARY KEY(IdEleve, IdAdmin)," & _
   "FOREIGN KEY(IdEleve) REFERENCES ELEVE(IdEleve)," & _
   "FOREIGN KEY(IdAdmin) REFERENCES ADMINISTRATEUR(IdAdmin)" & _
");"   

DoCmd.RunSQL "CREATE TABLE APPARTENIR(" & _
   "IdEleve INT," & _
   "IdClasse INT," & _
   "PRIMARY KEY(IdEleve, IdClasse)," & _
   "FOREIGN KEY(IdEleve) REFERENCES ELEVE(IdEleve)," & _
   "FOREIGN KEY(IdClasse) REFERENCES CLASSE(IdClasse)" & _
");"   

End Sub