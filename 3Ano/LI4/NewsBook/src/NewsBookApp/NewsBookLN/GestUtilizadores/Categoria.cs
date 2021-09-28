namespace NewsBookLN.GestUtilizadores
{
	public class Categoria
	{


		public Categoria()
		{
			IdCategoria = 0;
			Nome = "";
			Selected = false;
		}

		public Categoria(int idCat, string nome)
		{
			IdCategoria = idCat;
			Nome = nome;
		}

        public int IdCategoria { get; set; }
		public string Nome { get; set; }

		public bool Selected { get; set; }
	}
}