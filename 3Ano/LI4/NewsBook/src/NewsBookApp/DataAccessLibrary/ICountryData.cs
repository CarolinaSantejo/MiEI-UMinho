using NewsBookLN.GestUtilizadores;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace DataAccessLibrary
{
    public interface ICountryData
    {
        Task<List<Country>> GetPaises();
        Task<List<Country>> GetPais(string id);
    }
}