using System;

namespace NewsBookLN.GestPublicacoes
{

	public class Noticia : Publicacao
	{


        public Noticia() : base() 
		{ }
		public Noticia(int pubId, string titulo, DateTime data, string desc, string img, string url, string autor, string idPais) : base(pubId, titulo, data, desc, img)
		{
			Url = url;
			Autor = autor;
			IdPais = idPais;
		}

		public string Url { get; set; }
		public string Autor { get; set; }
		public string IdPais { get; set; }


		public override string ToString()
		{
			return base.ToString() + " url='" + Url + "'" + ", autor='" + Autor + "'" + ", idPais='" + IdPais + "'" + "}";
		}


	}
}