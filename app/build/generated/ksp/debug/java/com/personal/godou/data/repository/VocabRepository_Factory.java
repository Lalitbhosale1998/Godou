package com.personal.godou.data.repository;

import com.personal.godou.data.dao.VocabDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class VocabRepository_Factory implements Factory<VocabRepository> {
  private final Provider<VocabDao> vocabDaoProvider;

  private VocabRepository_Factory(Provider<VocabDao> vocabDaoProvider) {
    this.vocabDaoProvider = vocabDaoProvider;
  }

  @Override
  public VocabRepository get() {
    return newInstance(vocabDaoProvider.get());
  }

  public static VocabRepository_Factory create(Provider<VocabDao> vocabDaoProvider) {
    return new VocabRepository_Factory(vocabDaoProvider);
  }

  public static VocabRepository newInstance(VocabDao vocabDao) {
    return new VocabRepository(vocabDao);
  }
}
