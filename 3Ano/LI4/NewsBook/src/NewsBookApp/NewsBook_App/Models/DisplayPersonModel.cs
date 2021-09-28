using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace NewsBook_App.Models
{
    public class DisplayPersonModel
    {
        [Required(ErrorMessage = "Name field is required.")]
        public string Nome { get; set; }

        [Required(ErrorMessage = "Password field is required.")]
        [MinLength(5, ErrorMessage = "Password is too short.")]
        public string Password { get; set; }

        [Required(ErrorMessage = "Email address field is required.")]
        [EmailAddress]
        public string Email { get; set; }

        [Required(ErrorMessage = "UserName field is required.")]
        [MinLength(5, ErrorMessage = "UserName is too short.")]
        public string UserName { get; set; }
        public string Fotografia { get; set; }
        public string Cidade { get; set; }

        [Required(ErrorMessage = "Country field is required.")]
        public string Country_IdCountry { get; set; }

    }
}
