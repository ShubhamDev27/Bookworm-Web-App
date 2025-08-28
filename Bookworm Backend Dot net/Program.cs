using Microsoft.EntityFrameworkCore;
using Bookworm.Models; // Assuming your entities are here
using Pomelo.EntityFrameworkCore.MySql.Infrastructure;
// This using is needed for the MySQL options
using Bookworm.Repository; // Assuming your DbContext is here

namespace Bookworm
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var builder = WebApplication.CreateBuilder(args);

            // Add services to the container.
            builder.Services.AddControllers();
            builder.Services.AddEndpointsApiExplorer(); // Required for OpenAPI/Swagger


            // Correct DbContext configuration for MySQL
            var ConnectionString = builder.Configuration.GetConnectionString("DefaultConnection");
            builder.Services.AddDbContext<BookwormDbContext>(options =>
                options.UseMySql(ConnectionString,
                    ServerVersion.AutoDetect(ConnectionString))); // This specifies the MySQL provider

            // Add Authorization services
            builder.Services.AddAuthorization();

            var app = builder.Build();

            // Configure the HTTP request pipeline.


            app.UseHttpsRedirection();
            app.UseAuthorization(); // Corrected method name
            app.MapControllers();
            app.Run();
        }
    }
}