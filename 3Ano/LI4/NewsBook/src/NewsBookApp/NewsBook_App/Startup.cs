using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using DataAccessLibrary;
using DataLibrary;
using NewsBookLN;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Components;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Syncfusion.Blazor;


namespace NewsBook_App
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        // For more information on how to configure your application, visit https://go.microsoft.com/fwlink/?LinkID=398940
        public void ConfigureServices(IServiceCollection services)
        {
            services.AddRazorPages();
            services.AddServerSideBlazor();
            services.AddTransient<ISQLDataAccess, SQLDataAccess>();
            services.AddTransient<IPeopleData, PeopleData>();
            services.AddTransient<ICategoriaData, CategoriaData>();
            services.AddTransient<ICountryData, CountryData>();
            services.AddTransient<IJornalistaData, JornalistaData>();
            services.AddTransient<IArtigoData, ArtigoData>();
            services.AddTransient<IPublicacaoData, PublicacaoData>();
            services.AddSyncfusionBlazor();
            services.AddScoped<LoginState>();
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            Syncfusion.Licensing.SyncfusionLicenseProvider.RegisterLicense("NDU1NzY1QDMxMzkyZTMxMmUzMGY1bDdoQmNoYmNKSnRxU2FJYTUvcC8xT0xybkFyQVM0UXlpQXZlZFBSVVE9");
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }
            else
            {
                app.UseExceptionHandler("/Error");
            }

            app.UseStaticFiles();

            app.UseRouting();

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapBlazorHub();
                endpoints.MapFallbackToPage("/_Host"); 
            });
        }
    }
}
