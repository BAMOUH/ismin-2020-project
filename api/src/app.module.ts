import { HttpModule, Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { MongooseModule } from '@nestjs/mongoose';
import { StationAvailablityDocument, StationAvailablitySchema } from './Station.schema';
import { ScheduleModule } from '@nestjs/schedule';
import { cronGraberService } from './cronGraber.service';


require('dotenv').config();

@Module({
  imports: [
    HttpModule,
    ScheduleModule.forRoot(),
    MongooseModule.forRoot(process.env.DATABASE_URL,{ useFindAndModify: false }),
    MongooseModule.forFeature([
      { name: StationAvailablityDocument.name, schema: StationAvailablitySchema },
    ]),
  ],
  controllers: [AppController],
  providers: [AppService,cronGraberService],
})
export class AppModule {}
