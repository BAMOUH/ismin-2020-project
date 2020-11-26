import { Controller, Get, Param } from '@nestjs/common';
import { AppService } from './app.service';
import { StationInfos } from './StationInfos';

@Controller('stations')
export class AppController {
  constructor(private readonly appService: AppService) {}

  @Get()
  async getStations(): Promise<StationInfos[]>  {
    return this.appService.getStations();
  }

  @Get('/:stationId')
  async getStationById(@Param('stationId') stationId: number): Promise<StationInfos> {
    return this.appService.getStationById(stationId);
  }
}
