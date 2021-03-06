package org.borademir.eksici.api.test;

import java.io.IOException;
import java.text.ParseException;

import org.apache.log4j.Logger;
import org.borademir.eksici.api.EksiApiException;
import org.borademir.eksici.api.EksiApiServiceFactory;
import org.borademir.eksici.api.IEksiService;
import org.borademir.eksici.api.model.EksiLoginSuser;
import org.borademir.eksici.api.model.EntryModel;
import org.borademir.eksici.api.model.GenericPager;
import org.borademir.eksici.api.model.MainPageModel;
import org.borademir.eksici.api.model.SearchCriteriaModel;
import org.borademir.eksici.api.model.TopicModel;
import org.borademir.eksici.conf.EksiciResourceUtil;
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
		
		boolean processAutocomplete = false;
		
		boolean processSuser = false;
		
		boolean processEntryStats = false;
		
		boolean processLogin  = false;
		
		boolean processFavorites =false;
		
		boolean processMessages = false;
		
		if(processMessages){
			String loginUrl = EksiciResourceUtil.getLoginUrl();
			EksiLoginSuser loginSuser = eksiciService.login(loginUrl,"bora@jforce.com.tr","--");
			System.out.println(loginSuser.getSozlukToken());
			
			String targetUrl = EksiciResourceUtil.getMessageUrl();
			eksiciService.messages(targetUrl,loginSuser.getSozlukToken());
		}
		
		if(processPopulars){
			log.debug("Popular Topics:");
			String targetUrl = EksiciResourceUtil.getPopularTopicsUrl();
			for(;;){
				GenericPager<TopicModel> popularTopicCurrentPage = eksiciService.retrievePopularTopics(targetUrl);
				for(TopicModel tm : popularTopicCurrentPage.getContentList()){
					log.debug(tm.getTopicText() + "(" + tm.getRelatedEntryCount() + ") - " + tm.getHref());
				}
				if(popularTopicCurrentPage.getNextPageHref() != null){
					targetUrl = EksiciResourceUtil.getHeaderReferrer() +  popularTopicCurrentPage.getNextPageHref();
				}else{
					break;
				}
			}
		}
		
		if(processSuser){
			String targetUrl = EksiciResourceUtil.getSuserUrl("qlluq");
			eksiciService.suser(targetUrl);
		}
		
		if(processEntryStats){
			String targetUrl = EksiciResourceUtil.getUserEntryStatsUrl("son-entryleri", "qlluq", System.currentTimeMillis());
			eksiciService.suserEntryStats(targetUrl);
		}
		
		
		if(processFavorites){
			String loginUrl = EksiciResourceUtil.getLoginUrl();
			EksiLoginSuser loginSuser = eksiciService.login(loginUrl,"abc","pass");
			System.out.println(loginSuser.getSozlukToken());
			
			String targetUrl = EksiciResourceUtil.getFavoritesUrl(System.currentTimeMillis(), 68148607);
			eksiciService.favorites(targetUrl,loginSuser.getSozlukToken());
		}
		
		if(processTodays){
			log.debug("Todays Topics:");
			String targetUrl = EksiciResourceUtil.getPopularTopicsUrl();
			for(;;){
				GenericPager<TopicModel> popularTopicCurrentPage = eksiciService.retrieveTodaysTopics(targetUrl);
				for(TopicModel tm : popularTopicCurrentPage.getContentList()){
					log.debug(tm.getTopicText() + "(" + tm.getRelatedEntryCount() + ") - " + tm.getHref());
				}
				if(popularTopicCurrentPage.getNextPageHref() != null){
					targetUrl = EksiciResourceUtil.getHeaderReferrer() +  popularTopicCurrentPage.getNextPageHref();
				}else{
					break;
				}
			}
		}
		
		
		if(processDeserted){
			log.debug("Deserted Topics:");
			String targetUrl = EksiciResourceUtil.getPopularTopicsUrl();
			for(;;){
				GenericPager<TopicModel> popularTopicCurrentPage = eksiciService.retrieveDesertedTopics(targetUrl);
				for(TopicModel tm : popularTopicCurrentPage.getContentList()){
					log.debug(tm.getTopicText() + "(" + tm.getRelatedEntryCount() + ") - " + tm.getHref());
				}
				if(popularTopicCurrentPage.getNextPageHref() != null){
					targetUrl = EksiciResourceUtil.getHeaderReferrer() +  popularTopicCurrentPage.getNextPageHref();
				}else{
					break;
				}
			}
		}
		
		if(processTodayInHistory){
			log.debug("Today In History Topics:");
			String targetUrl = EksiciResourceUtil.getTodayInHistoryTopicsUrl(System.currentTimeMillis(), 2002);
			for(;;){
				GenericPager<TopicModel> popularTopicCurrentPage = eksiciService.retrieveTodayInHistoryTopics(targetUrl);
				for(TopicModel tm : popularTopicCurrentPage.getContentList()){
					log.debug(tm.getTopicText() + "(" + tm.getRelatedEntryCount() + ") - " + tm.getHref());
				}
				if(popularTopicCurrentPage.getNextPageHref() != null){
					targetUrl = EksiciResourceUtil.getHeaderReferrer() +  popularTopicCurrentPage.getNextPageHref();
				}else{
					break;
				}
			}
		}
		
//		if(processChannels){
//			log.debug("Channels:");
//			List<ChannelModel> channels = eksiciService.retrieveChannels();
//			for(ChannelModel channel : channels){
//				log.debug(channel.getName() + " (" + channel.getTitle() + ") -- " + channel.getHref() );
//				GenericPager<TopicModel> channelTopics = null;
//				while((channelTopics = eksiciService.retrieveChannelTopics(channel)) != null){
//				}
//			}
//		}
		
		
		if(processVideos){
			log.debug("Videos:");
			String targetUrl = EksiciResourceUtil.getVideosUrl(System.currentTimeMillis());
			for(;;){
				GenericPager<TopicModel> currentPage = eksiciService.retrieveVideos(targetUrl);
				for(TopicModel tm : currentPage.getContentList()){
					log.debug(tm.getTopicText() + "(" + tm.getRelatedEntryCount() + ") - " + tm.getHref());
				}
				if(currentPage.getNextPageHref() != null){
					targetUrl = EksiciResourceUtil.getHeaderReferrer() +  currentPage.getNextPageHref();
				}else{
					break;
				}
			}
		
		}
		
		if(processSearch){
			SearchCriteriaModel searchCriteriaModel = new SearchCriteriaModel();
			searchCriteriaModel.setKeywords("aykut"); 
//			searchCriteriaModel.setAuthor("qlluq");
			mainPage.setSearchCriteria(searchCriteriaModel);
			log.debug(searchCriteriaModel);
			log.debug("Search results:");
			GenericPager<TopicModel> searchResults = null;
			while((searchResults = eksiciService.search(mainPage)) != null){
				for(TopicModel tm : searchResults.getContentList()){
					log.debug(tm.getTopicText() + "(" + tm.getRelatedEntryCount() + ") - " + tm.getOriginalUrl());
					GenericPager<EntryModel> currentPage = null;
//					while((currentPage = eksiciService.retrieveEntries(tm,null)) != null){
//						printEntryModel(currentPage);
//					}
				}
			}
		}
		
		if(processAutocomplete){
			String pUrl = EksiciResourceUtil.getAutocompleteUrl(System.currentTimeMillis(),"aykut");
			eksiciService.autocomplete(pUrl);
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
			 log.debug("\t\t\tEntryDate:" + entryModel.getEntryDate());
			 log.debug("\t\t\tEntryLink:" + entryModel.getEntryHref());
			 
			 log.debug("\t\t\tAuthor:" + entryModel.getSuser().getNick());
			 log.debug("\t\t\tAuthorId:" + entryModel.getSuser().getSuserId());
			 log.debug("\t\t\tAuthorLink:" + entryModel.getSuser().getHref());
			 
			 if(maxFav == null || entryModel.getFavoriteCount() > maxFav.getFavoriteCount()){
				 maxFav = entryModel;
			 }
		 
		}
	}
	


}
