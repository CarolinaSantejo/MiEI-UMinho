using DataLibrary;
using NewsBookLN.GestUtilizadores;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccessLibrary
{
    public class CategoriaData : ICategoriaData
    {
        private readonly ISQLDataAccess _dbCat;

        public CategoriaData(ISQLDataAccess db)
        {
            _dbCat = db;
        }
        
        public Task<List<Categoria>> GetCategorias()
        {
            string sql = "select * from dbo.Categoria";
            return _dbCat.LoadData<Categoria, dynamic>(sql, new { });
        }

        public Task<List<Categoria>> GetCategoria(int cat)
        {
            string sql = "select * from dbo.Categoria where IdCategoria = '" + cat + "'";
            return _dbCat.LoadData<Categoria, dynamic>(sql, new { });
        }

        public Task InsertUserCategorias(Utilizador person)
        {
            List <int> c = person.CategoriasFav;
            int cat = c[0];
            string sql = @"insert into dbo.Utilizador_Categoria (UserName,IdCategoria)
                          values ";
            for (int i = 0; i < c.Count()-1; i++)
            {
                sql = sql + "('" + person.UserName + "', '" + c[i] + "'), ";
            }
            sql = sql + "('" + person.UserName + "', '" + c[c.Count()-1] + "')";
            return _dbCat.SaveData(sql, new { });
        }

        public Task<List<Categoria>> GetCategoriaUser(string u)
        {
            string sql = "select * from dbo.Utilizador_Categoria where UserName = '" + u + "'";
            return _dbCat.LoadData<Categoria, dynamic>(sql, new { });
        }
        public Task deleteCategoriasFav(string username, List<int> idCats)
        {
            string sql = "";
            foreach (int cat in idCats)
                sql += "delete from dbo.Utilizador_Categoria where IdCategoria='" + cat + "' and UserName ='" + username + "';";

            return _dbCat.SaveData(sql, new { });
        }
    }
}
