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
      let data  = await this.http.get(process.env.STATION_AVAILABILITY_API_URL).pipe(
        map(response => response.data),
      );
      let response = data.toPromise();
      response.then(data =>{
        let StationEntities : Array<any> = data.data.stations;
        
        StationEntities.map(oneStationEntity => this.handleUpdateOrSend(oneStationEntity,data.lastUpdatedOther));
      }).catch(
        error => this.logger.debug("Error" +error)
      );
    } catch (error) {
      this.logger.debug("Error" +error);
      
    }
    await this.logger.debug('Done refetching data !');
   
  }

  async handleUpdateOrSend(oneStationEntity , lastUpdatedOther : number){
    let stationAv : StationAvailability={
      station_id: oneStationEntity.station_id,
      stationCode: oneStationEntity.stationCode,
      mechanical: oneStationEntity.num_bikes_available_types[0].mechanical,
      ebike: oneStationEntity.num_bikes_available_types[1].ebike,
      numDocksAvailable: oneStationEntity.numDocksAvailable,
      last_reported: oneStationEntity.last_reported,
      lastUpdatedOther: lastUpdatedOther,
    } 
    
    let query = { 
      station_id: stationAv.station_id
    };
    let update = stationAv;
    let options = {upsert: true, new: true, setDefaultsOnInsert: true};
    await this.StationAvailabilityModel.findOneAndUpdate(query, update, options);
  }
}