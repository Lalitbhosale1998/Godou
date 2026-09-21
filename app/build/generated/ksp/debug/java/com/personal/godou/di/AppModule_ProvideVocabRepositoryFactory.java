package com.personal.godou.di;

import com.personal.godou.data.dao.VocabDao;
import com.personal.godou.data.repository.VocabRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class AppModule_ProvideVocabRepositoryFactory implements Factory<VocabRepository> {
  private final Provider<VocabDao> vocabDaoProvider;

  private AppModule_ProvideVocabRepositoryFactory(Provider<VocabDao> vocabDaoProvider) {
    this.vocabDaoProvider = vocabDaoProvider;
  }

  @Override
  public VocabRepository get() {
    return provideVocabRepository(vocabDaoProvider.get());
  }

  public static AppModule_ProvideVocabRepositoryFactory create(
      Provider<VocabDao> vocabDaoProvider) {
    return new AppModule_ProvideVocabRepositoryFactory(vocabDaoProvider);
  }

  public static VocabRepository provideVocabRepository(VocabDao vocabDao) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideVocabRepository(vocabDao));
  }
}
