USE NewsBookDB
GO

INSERT INTO [dbo].[Country] ([IdCountry],[Nome])
     VALUES
             ('pt','Portugal'),
            ('us','United States of America'),
            ('de','Deutschland'),
            ('gb','The United Kingdom')
            
            
            
INSERT INTO [dbo].[Categoria] ([IdCategoria],[Nome])
     VALUES
            (1,'Business'),
            (2,'Technology'),
            (3,'Entertainment'),
            (4,'Health'),    
            (5,'Sports'),
            (6,'Science')
            
            
 INSERT INTO [dbo].[Utilizador] ([UserName],[Email],[Password],[Nome],[Cidade],[Fotografia],[Country_IdCountry])
     VALUES
            ('CarolinaSantejo','caliSantejo@gmail.com','batatasFritas','Carolina','Beja',null,'pt'),
            ('KelinhaZuca','ZucaBazuca@gmail.com','ipanema','Raquel','Lisboa',null,'pt'),
            ('FilipaPereira','filipaPereira@gmail.com','fp2000','Filipa','London',null,'gb') 
            
  
  
INSERT INTO [dbo].[Utilizador_Categoria] ([UserName],[IdCategoria])
     VALUES
            ('CarolinaSantejo',1),
            ('CarolinaSantejo',6),
            ('KelinhaZuca',4),
            ('KelinhaZuca',6),
            ('KelinhaZuca',2)
            
     
INSERT INTO [dbo].[Jornalista] ([UserNameJornalista],[Biografia],[LinkedIn])
     VALUES
            ('FilipaPereira','I became a journalist to come as close as possible to the heart of the world',null)        
            
            
INSERT INTO [dbo].[Utilizador_Jornalista] ([UserName],[UserNameJornalista])
     VALUES
            ('CarolinaSantejo','FilipaPereira'),
            ('KelinhaZuca','FilipaPereira')           
GO