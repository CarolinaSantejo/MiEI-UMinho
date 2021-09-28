using NewsBookLN.GestUtilizadores;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace DataAccessLibrary
{
    public interface IJornalistaData
    {
        Task<List<Jornalista>> GetJornalistas();
        Task<List<Jornalista>> GetJornalista(string id);
        Task InsertJornalista(Jornalista person);

        Task<List<string>> GetJornalistasSeguidos(string userName);
        Task updateUserProfile(Jornalista person);
        Task<List<string>> GetSeguidoresJornalista(string userName);
    }
}