using DataLibrary;
using NewsBookLN.GestUtilizadores;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccessLibrary
{
    public class JornalistaData : IJornalistaData
    {
        private readonly ISQLDataAccess _dbJ;

        public JornalistaData(ISQLDataAccess db)
        {
            _dbJ = db;
        }

        public Task<List<Jornalista>> GetJornalistas()
        {
            string sql = "select * from dbo.Jornalista";
            return _dbJ.LoadData<Jornalista, dynamic>(sql, new { });
        }

        public Task<List<Jornalista>> GetJornalista(string id)
        {
            string sql = "select * from dbo.Jornalista where UserNameJornalista = '" + id + "'";
            return _dbJ.LoadData<Jornalista, dynamic>(sql, new { });
        }

        public Task InsertJornalista(Jornalista person)
        {
            string sql = @"insert into dbo.Jornalista (UserNameJornalista,Biografia,LinkedIn)
                          values (@UserName,null,null);";
            return _dbJ.SaveData(sql, person);
        }

        public Task<List<string>> GetJornalistasSeguidos(string userName)
        {
            string sql = "select UserNameJornalista from dbo.Utilizador_Jornalista where UserName='" + userName + "'";
            return _dbJ.LoadData<string, dynamic>(sql, new { });
        }

        public Task<List<string>> GetSeguidoresJornalista(string userName)
        {
            string sql = "select UserName from dbo.Utilizador_Jornalista where UserNameJornalista='" + userName + "'";
            return _dbJ.LoadData<string, dynamic>(sql, new { });
        }

        public Task updateUserProfile(Jornalista person)
        {
            Console.WriteLine("Bio: "+ person.UserName);
            string sql = "update dbo.Jornalista set ";
            sql = sql + "Biografia = @Biografia,";
            sql = sql + "LinkedIn = @LinkedIn";
            sql = sql + " where UserNameJornalista = @UserName;";

            return _dbJ.SaveData(sql, person);
        }
    }
}
