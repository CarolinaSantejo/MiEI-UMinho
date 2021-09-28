using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DataLibrary;
using NewsBookLN.GestUtilizadores;

namespace DataAccessLibrary
{
    public class CountryData : ICountryData
    {
        private readonly ISQLDataAccess _dbC;

        public CountryData(ISQLDataAccess db)
        {
            _dbC = db;
        }

        public Task<List<Country>> GetPais(string id)
        {
            string sql = "select * from dbo.Country where IdCountry = '" + id + "'"; 
            return _dbC.LoadData<Country, dynamic>(sql, new { });
        }

        public Task<List<Country>> GetPaises()
        {
            string sql = "select * from dbo.Country";
            return _dbC.LoadData<Country, dynamic>(sql, new { });
        }
    }
}
