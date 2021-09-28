using System;
using System.Collections.Generic;

namespace NewsBookLN.GestPublicacoes
{
	
	public class Publicacao
	{

		public Publicacao() {
			Comentarios = new List<Comentario>();
		}
		public Publicacao(int pubId, string titulo, DateTime data, string desc, string img)
		{
			IdPublicacao = pubId;
			Titulo = titulo;
			Data = data;
			Descricao = desc;
			Imagem = img;
			Comentarios = new List<Comentario>();
		}

		public Publicacao(string titulo, DateTime data, string desc, string img)
		{
			IdPublicacao = idPubGenerator();
			Titulo = titulo;
			Data = data;
			Descricao = desc;
			Imagem = img;
			Comentarios = new List<Comentario>();
		}

		public ICollection<Comentario> Comentarios { get; set; }
		public int IdPublicacao { get; set; }
		public string Titulo { get; set; }
		public DateTime Data { get; set; }
		public string Descricao { get; set; }
		public string Imagem { get; set; }


		public virtual int idPubGenerator()
		{
			DateTime data = DateTime.Now;
			DateTime dataI = new DateTime(2020, 1, 1, 0, 0, 0);
			TimeSpan ts = data - dataI;
			int min = (int)ts.TotalSeconds;

			String format = "2" + min.ToString();
			int dataID = int.Parse(format);

			return dataID;
		}


		public override string ToString()
		{
			return "{" + " comentarios='" + Comentarios + "'" + ", pubID='" + IdPublicacao + "'" + ", titulo='" + Titulo + "'" + ", data='" + Data + "'" + ", descricao='" + Descricao + "'" + ", imagem='" + Imagem + "'";
		}

	}
}