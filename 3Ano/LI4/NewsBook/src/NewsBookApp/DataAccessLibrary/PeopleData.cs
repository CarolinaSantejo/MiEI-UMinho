using NewsBookLN.GestPublicacoes;
using NewsBookLN.GestUtilizadores;
using DataLibrary;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccessLibrary
{
    public class PeopleData : IPeopleData
    {
        private readonly ISQLDataAccess _db;

        public PeopleData(ISQLDataAccess db)
        {
            _db = db;
        }

        public Task<List<Utilizador>> GetPerson(String userName)
        {
            string sql = "select * from dbo.Utilizador where UserName = '"+ userName+"'";
            return _db.LoadData<Utilizador, dynamic>(sql, new { });
        }

        public Task<List<Utilizador>> GetPeople()
        {
            string sql = "select * from dbo.Utilizador";
            return _db.LoadData<Utilizador, dynamic>(sql, new { });
        }

        public Task InsertPerson(Utilizador person)
        {
            string sql = @"insert into dbo.Utilizador (UserName,Email,Password,Nome,Cidade,Fotografia,Country_IdCountry)
                          values (@UserName, @Email, @Password, @Nome, @Cidade, @Fotografia, @Country_IdCountry);";
            return _db.SaveData(sql, person);
        }

        public Task RegisterNewPerson(Utilizador person)
        {
            string sql = @"insert into dbo.Utilizador (UserName,Email,Password,Nome,Cidade,Fotografia,Country_IdCountry)
                          values (@UserName, @Email, @Password, null, null, null, @Country_IdCountry);";
            return _db.SaveData(sql, person);
        }
        public Task InsertComment(Comentario c)
        {
            string sql = @"insert into dbo.Comentario (UserName,IdPublicacao,Texto,Data)
                          values (@UserName,@IdPublicacao,@Texto,@Data);";
            return _db.SaveData(sql, c);
        }

        public Task UpdateComment(Comentario c)
        {
            string sql = @"update dbo.Comentario set IdPublicacao = @IdPublicacao, Texto = @Texto, Data=@Data where UserName=@UserName;";
            return _db.SaveData(sql, c);
        }

        public Task updateUserProfile(Utilizador person)
        {
            string sql = "update dbo.Utilizador set ";
            sql = sql + "Nome = @Nome,";
            sql = sql + "Password = @Password,";
            sql = sql + "Country_IdCountry = @Country_IdCountry,";
            sql = sql + "Cidade = @Cidade";
            sql = sql + " where UserName = @UserName;";

            return _db.SaveData(sql, person);
        }

        public Task<List<Comentario>> GetComentarios(int idPub)
        {
            string sql = "select * from dbo.Comentario where IdPublicacao = '" + idPub + "'";
            return _db.LoadData<Comentario, dynamic>(sql, new { });
        }

        public Task<List<int>> getFavCategouries(string userName)
        {
            string sql = "select IdCategoria from dbo.Utilizador_Categoria where UserName = '" + userName + "'";
            return _db.LoadData<int, dynamic>(sql, new { });
        }

        public Task InsertCategoriaFav(string username,int idCat)
        {
            string sql = @"insert into dbo.Utilizador_Categoria (UserName,IdCategoria)
                          values ('"+username+"','"+idCat+"');";

            return _db.SaveData(sql, new { });
        }

        public Task<List<Notificacao>> GetNotifications(string userName)
        {
            string sql = "select * from dbo.Notificacao where UserName = '" + userName + "'";
            return _db.LoadData<Notificacao, dynamic>(sql, new { });
        }
        public Task InsertNotification(Notificacao n)
        {
            string sql = @"insert into dbo.Notificacao (Data,IdPublicacao,UserName)
                          values (@Data,@IdPublicacao,@UserName);";
            return _db.SaveData(sql, n);
        }

        public Task deleteAllNotifications(string username)
        {
            string sql = @"delete from dbo.Notificacao where UserName ='" + username + "';";

            return _db.SaveData(sql, new { });
        }
        public Task InsertFollow(string username, string jornalista)
        {
            string sql = @"insert into dbo.Utilizador_Jornalista (UserName,UserNameJornalista)
                          values ('" + username + "','" + jornalista + "');";

            return _db.SaveData(sql, new { });
        }

        public Task RemoveFollow(string username, string jornalista)
        {
            string sql = @"delete from dbo.Utilizador_Jornalista where UserName = '" + username + "'  and " + " UserNameJornalista= '"
                + jornalista + "'";

            return _db.SaveData(sql, new { });
        }
    }

    
}
