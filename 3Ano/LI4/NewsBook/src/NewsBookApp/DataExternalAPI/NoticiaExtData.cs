using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;

namespace DataExternalAPI
{
	using Noticia = NewsBookLN.GestPublicacoes.Noticia;
	public class NoticiaExtData
	{
		public string pais;
		public IDictionary<int, Noticia> noticias;
		public string key = "9166fe9252c243d78d6f479abb31b77e";


		public NoticiaExtData(string pais)
		{
			this.pais = pais;
			this.noticias = new SortedDictionary<int, Noticia>();
		}




		private JObject readJsonFromUrl(string url)
		{

			using (WebClient client = new WebClient())
			{
				string s = client.DownloadString(url);
				JObject stuff = JObject.Parse(s);
				return stuff;
			}

		}


		public virtual void getTopHeadlines()
		{
			JObject json = readJsonFromUrl("https://newsapi.org/v2/top-headlines?country=" + pais + "&apiKey=" + key);
			updateNoticias(json);
		}


		public virtual void getByCategory(string categoria)
		{
			JObject json = readJsonFromUrl("https://newsapi.org/v2/top-headlines?country=" + pais + "&category=" + categoria + "&apiKey=" + key);
			updateNoticias(json);
		}


		public virtual void getByKeyWord(string keyWord)
		{
			JObject json = readJsonFromUrl("https://newsapi.org/v2/everything?q=" + keyWord + "&apiKey=" + key);
			updateNoticias(json);
		}

		private void updateNoticias(JObject json)
		{

			noticias = new SortedDictionary<int, Noticia>();
			JArray articles = (JArray)json["articles"];
			int count = articles.Count;
			for (int i = 0; i < count; i++)
			{

				JObject artigo = (JObject)articles[i];
				string autor = (string)artigo["author"];
				if (autor == null)
				{
					autor = "Anonimo";
				}
				DateTime dataPublicacao = (DateTime)artigo["publishedAt"];
				string contTotal = (string)artigo["content"];
				if (contTotal != null)
				{
					string[] content = ((string)artigo["content"]).Split("[");
					contTotal = content[0];
				}
				string title = (string)artigo["title"];
				Noticia n = new Noticia
				{
					IdPublicacao = idNoticiaGenerator(dataPublicacao, (int)title[0]),
					Titulo = title,
					Data = dataPublicacao,
					Descricao = contTotal,
					Url = (string)artigo["url"],
					Imagem = (string)artigo["urlToImage"],
					Autor = autor,
					IdPais = Pais
				};
				
				noticias.Add(n.IdPublicacao, n);
			}
		}

		public virtual int idNoticiaGenerator(DateTime data,int k)
		{
			DateTime dataI = new DateTime(2020, 1, 1, 0, 0, 0);
			TimeSpan ts = data - dataI;
			int min = (int)ts.TotalSeconds;
			
			String format = "1" + min.ToString();
			int dataID = int.Parse(format) + k;

			return dataID;
		}

		public virtual string Pais
		{
			get
			{
				return this.pais;
			}
			set
			{
				this.pais = value;
			}
		}


		public virtual IDictionary<int, Noticia> Noticias
		{
			get
			{
				return this.noticias;
			}
			set
			{
				this.noticias = value;
			}
		}
	}
}
