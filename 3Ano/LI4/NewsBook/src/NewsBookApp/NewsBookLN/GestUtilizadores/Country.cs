namespace NewsBookLN.GestUtilizadores
{
	public class Country
	{

		public Country()
		{
			IdCountry = "";
			Nome = "";
		}
		public Country(string idPais, string nome)
		{
			IdCountry = idPais;
			Nome = nome;
		}

        public string IdCountry { get; set; }
		public string Nome { get; set; }
	}
}