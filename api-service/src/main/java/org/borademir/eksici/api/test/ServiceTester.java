package org.borademir.eksici.api.test;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.apache.log4j.Logger;
import org.borademir.eksici.api.EksiApiException;
import org.borademir.eksici.api.EksiApiServiceFactory;
import org.borademir.eksici.api.IEksiService;
import org.borademir.eksici.api.model.ChannelModel;
import org.borademir.eksici.api.model.EntryModel;
import org.borademir.eksici.api.model.GenericPager;
import org.borademir.eksici.api.model.MainPageModel;
import org.borademir.eksici.api.model.SearchCriteriaModel;
import org.borademir.eksici.api.model.TopicModel;
import org.borademir.eksici.util.EksiciDateUtil;
/**
 * @author bora.demir
 */
public class ServiceTester {
	
	static Logger log = Logger.getLogger(ServiceTester.class);
	
	static EntryModel maxFav = null;
	
	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException, ParseException, EksiApiException {
		
		IEksiService eksiciService = EksiApiServiceFactory.createService();
		
		MainPageModel mainPage = new MainPageModel();
		
		boolean processPopulars = true;
		boolean processTodays = false;
		boolean processDeserted = false;
		boolean processTodayInHistory = false;
		boolean processChannels = false;
		boolean processVideos = false;
		
		boolean processSearch = false;
		
		
		
		if(processPopulars){
			GenericPager<TopicModel> popularTopicCurrentPage = null;
			log.debug("Popular Topics:");
			while((popularTopicCurrentPage = eksiciService.retrievePopularTopics(mainPage)) != null){
				for(TopicModel tm : popularTopicCurrentPage.getContentList()){
					log.debug(tm.getTopicText() + "(" + tm.getRelatedEntryCount() + ") - " + tm.getHref());
//				GenericPager<EntryModel> currentPage = null;
//				while((currentPage = eksiciService.retriveEntries(tm,null)) != null){
//					printEntryModel(currentPage);
//				}
				}
			}
		}


		if(processTodays){
			log.debug("Todays Topics:");
			GenericPager<TopicModel> todaysTopicCurrentPage = null;
			while((todaysTopicCurrentPage = eksiciService.retrieveTodaysTopics(mainPage)) != null){
				for(TopicModel tm : todaysTopicCurrentPage.getContentList()){
					log.debug(tm.getTopicText() + "(" + tm.getRelatedEntryCount() + ") - " + tm.getHref());
//				GenericPager<EntryModel> currentPage = null;
//				while((currentPage = eksiciService.retriveEntries(tm,null)) != null){
//					printEntryModel(currentPage);
//				}
				}
			}
		}
		
		
		if(processDeserted){
			log.debug("Deserted Topics:");
			GenericPager<TopicModel> desertedTopicCurrentPage = null;
			while((desertedTopicCurrentPage = eksiciService.retrieveDesertedTopics(mainPage)) != null){
				for(TopicModel tm : desertedTopicCurrentPage.getContentList()){
					log.debug(tm.getTopicText() + "(" + tm.getRelatedEntryCount() + ") - " + tm.getOriginalUrl());
//				GenericPager<EntryModel> currentPage = null;
//				while((currentPage = eksiciService.retriveEntries(tm,null)) != null){
//					printEntryModel(currentPage);
//				}
//				break;
				}
			}
		}
		
		if(processTodayInHistory){
			log.debug("Today In History Topics:");
			GenericPager<TopicModel> desertedTopicCurrentPage = null;
			while((desertedTopicCurrentPage = eksiciService.retrieveTodayInHistoryTopics(mainPage,1999)) != null){
				for(TopicModel tm : desertedTopicCurrentPage.getContentList()){
					log.debug(tm.getTopicText() + "(" + tm.getRelatedEntryCount() + ") - " + tm.getHref());
//					GenericPager<EntryModel> currentPage = null;
//					while((currentPage = eksiciService.retriveEntries(tm,null)) != null){
//						printEntryModel(currentPage);
//					}
//					break;
					}
			}
		}
		
		if(processChannels){
			log.debug("Channels:");
			List<ChannelModel> channels = eksiciService.retrieveChannels();
			for(ChannelModel channel : channels){
				log.debug(channel.getName() + " (" + channel.getTitle() + ") -- " + channel.getHref() );
				GenericPager<TopicModel> channelTopics = null;
				while((channelTopics = eksiciService.retrieveChannelTopics(channel)) != null){
//				for(TopicModel tm : channelTopics.getContentList()){
//					log.debug(tm.getTopicText() + "(" + tm.getTopicPopularEntryCount() + ") - " + tm.getHref());
//				}
				}
			}
		}
		
		
		if(processVideos){
			log.debug("Video Topics:");
			GenericPager<TopicModel> videoTopicCurrentPage = null;
			while((videoTopicCurrentPage = eksiciService.retrieveVideos(mainPage)) != null){
				for(TopicModel tm : videoTopicCurrentPage.getContentList()){
					log.debug(tm.getTopicText() + "(" + tm.getRelatedEntryCount() + ") - " + tm.getOriginalUrl());
					GenericPager<EntryModel> currentPage = null;
					while((currentPage = eksiciService.retrieveEntries(tm,null)) != null){
						printEntryModel(currentPage);
					}
//					break;
				}
			}
		}
		
		if(processSearch){
			SearchCriteriaModel searchCriteriaModel = new SearchCriteriaModel();
			searchCriteriaModel.setKeywords("aykut"); 
			searchCriteriaModel.setAuthor("qlluq");
			mainPage.setSearchCriteria(searchCriteriaModel);
			log.debug(searchCriteriaModel);
			log.debug("Search results:");
			GenericPager<TopicModel> searchResults = null;
			while((searchResults = eksiciService.search(mainPage)) != null){
				for(TopicModel tm : searchResults.getContentList()){
					log.debug(tm.getTopicText() + "(" + tm.getRelatedEntryCount() + ") - " + tm.getOriginalUrl());
					GenericPager<EntryModel> currentPage = null;
					while((currentPage = eksiciService.retrieveEntries(tm,null)) != null){
						printEntryModel(currentPage);
					}
				}
			}
		}
		
		
//		log.debug(maxFav.getEntryHref());
//		log.debug(maxFav.getEntryText());
//		log.debug(maxFav.getFavoriteCount());
	}
	
	public static void printEntryModel(GenericPager<EntryModel> current) throws ParseException{
		log.debug("\tPage: " + current.getCurrentPage());
		for(EntryModel entryModel : current.getContentList()){
			 log.debug("\t\t" + entryModel.getEntryText());
			 log.debug("\t\t\tEntryId:" + entryModel.getEntryId());
			 log.debug("\t\t\tFavoriteCount:" + entryModel.getFavoriteCount());
			 log.debug("\t\t\tEntryDate:" + EksiciDateUtil.parseEntryDate(entryModel.getEntryDate()));
			 log.debug("\t\t\tEntryLink:" + entryModel.getEntryHref());
			 
			 log.debug("\t\t\tAuthor:" + entryModel.getSuser().getEntryAuthor());
			 log.debug("\t\t\tAuthorId:" + entryModel.getSuser().getEntryAuthorId());
			 log.debug("\t\t\tAuthorLink:" + entryModel.getSuser().getHref());
			 
			 if(maxFav == null || entryModel.getFavoriteCount() > maxFav.getFavoriteCount()){
				 maxFav = entryModel;
			 }
		 
		}
	}
	


}
