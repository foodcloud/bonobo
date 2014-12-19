<img src="https://github.com/foodcloud/bonobo/blob/master/public/images/bonobo.png" align="left" height="48"><h1>Bonobo</h1>

## Platform supporting food-sharing programmes

The Bonobo platform is a server/datastore supporting the activities of food-sharing (charity) intermediary organisations.

Initially, functionality is based upon the needs and business model of [FoodCloud.ie](http://foodcloud.ie/), a pioneering not-for-profit organization in Ireland, working to link food surpluses from commercial organizations (supermarkets, producers) with charities needing food.  They have been very successful and as a result the orignal systems put in place are no longer capable of meeting increased demands - a large amount of manual intervention in the form of ETL, off-line spreadheets is required to run the business.

This project seeks to analyse the current needs, predict future directions, and through a series of experimental prototypes, establish a robust and flexible server solution.
It is hoped that other, similar organizations can participate in framing the requirements and utilizing the software developed.

Current work is focused under the **documentation** folder, creating design materials, guided by principles of Domain Driven Design.

This project also contains a Play2 app, deploying to [Heroku's](https://www.heroku.com/) PaaS and linked to a datastore hosted by [MongoLabs](https://mongolab.com/), that is being used as a test-bench for ideas and solutions.  Server code is written in the Scala language, and client code currently uses jQuery as well as Play's Scala-based templating language.
