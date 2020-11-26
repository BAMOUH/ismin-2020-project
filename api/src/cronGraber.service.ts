import { Injectable, Logger ,HttpService} from '@nestjs/common';
import { InjectModel } from '@nestjs/mongoose';
import { Cron } from '@nestjs/schedule';
import { Model } from 'mongoose';
import { StationAvailablityDocument } from './Station.schema';
import { StationAvailability } from './StationAvailability';
import { map } from 'rxjs/operators';


@Injectable()
export class cronGraberService {
  
  private readonly logger = new Logger(cronGraberService.name);
  constructor(
    @InjectModel(StationAvailablityDocument.name) private StationAvailabilityModel: Model<StationAvailablityDocument>,
    private http: HttpService,
  ) {}


  @Cron('45 * * * * *')
  async handleCron() {
    this.logger.debug('Refetching data...');

    try {
      let data  = await this.http.get('https://velib-metropole-opendata.smoove.pro/opendata/Velib_Metropole/station_status.json').toPromise();        
      await this.logger.debug(data);
    } catch (error) {
      this.logger.debug(error);
      
    }
   
    
    
    // let stationAv : StationAvailability={
    //   station_id: number;
    //   stationCode: string;
    //   mechanical: number;
    //   ebike: number;
    //   numDocksAvailable: number;
    //   last_reported: number;
    //   lastUpdatedOther: number;
    // } 
    
    // let query = { 
    //   station_id: 124455
    // };
    // let update = {stationCode:"2121"};
    // let options = {upsert: true, new: true, setDefaultsOnInsert: true};
    // let model = await this.StationAvailabilityModel.findOneAndUpdate(query, update, options);
  }
}